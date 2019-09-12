package io.tstud.paperweight.Model;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.tstud.paperweight.Model.Dao.BookDao;
import io.tstud.paperweight.Model.Dao.CurrentlyReadingDao;
import io.tstud.paperweight.Model.Dao.RecentlySearchedDao;
import io.tstud.paperweight.Model.Local.Database;
import io.tstud.paperweight.Model.Models.BookWithStats;
import io.tstud.paperweight.Model.Models.Collection;
import io.tstud.paperweight.Model.Models.CurrentlyReadingStats;
import io.tstud.paperweight.Model.Models.Item;
import io.tstud.paperweight.Model.Models.RecentlySearchedBook;
import io.tstud.paperweight.Model.Network.GoogleBooksService;
import io.tstud.paperweight.Model.Network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Repository {

    private static final String TEST_BOOK_ID = "ibTVAQAAQBAJ";

    private static volatile Repository instance;
    private Retrofit apiClient;
    private GoogleBooksService apiService;
    private Database db;
    private BookDao bookDao;
    private CurrentlyReadingDao currentlyReadingDao;
    private RecentlySearchedDao recentlySearchedDao;

    private MutableLiveData<Item> bookItem = new MutableLiveData<>();
    private MutableLiveData<List<Item>> recentSearches = new MutableLiveData<>();
    private MutableLiveData<Collection> collectionTrending = new MutableLiveData<>();
    private MutableLiveData<List<Item>> searchedVolumes = new MutableLiveData<>();
    private MutableLiveData<List<BookWithStats>> booksWithStats = new MutableLiveData<>();

    private CompositeDisposable disposables = new CompositeDisposable();
    private Disposable deleteDisposable, readDisposable;


    private Repository() {
        apiClient = RetrofitClient.getInstance();
        apiService = apiClient.create(GoogleBooksService.class);
        db = Database.getInstance();
        bookDao = db.bookDao();
        currentlyReadingDao = db.currentlyReadingDao();
        recentlySearchedDao = db.recentlySearchedDao();
    }

    public static synchronized Repository getInstance() {
        if (instance == null)
            instance = new Repository();

        return instance;
    }

    public MutableLiveData<Item> getBookDetails(String bookToFetch) {
        Observable.concat(getSingleLocalTest(bookToFetch), getSingleNetworkTest(bookToFetch))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .firstElement()
                .doOnSuccess(item -> bookItem.setValue(item))
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .subscribe();

        return bookItem;
    }

    public Observable<Item> getSingleNetworkTest(String id) {
        return apiService.getSingleVolumeById(id)
                .doOnSuccess(item -> {
                    saveBookToLocal(item);
                    Log.d("Book fetched network", item.getId());
                })
                .toObservable();
    }

    public Observable<Item> getSingleLocalTest(String id) {
        return db.bookDao().getBookFromLocal(id)
                .doOnSuccess(item -> Log.d("Book fetched database", item.getId()))
                .toObservable();
    }


    public MutableLiveData<Collection> getTrendingList() {

        apiService.getVolumes("sapkowski", "relevance").enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                collectionTrending.postValue(response.body());
                Log.d("repo", "fetched new content");
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

        return collectionTrending;
    }

    public MutableLiveData<List<Item>> getSearchVolumes(String query) {

        apiService.getVolumes(query, "relevance").enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                searchedVolumes.postValue(response.body().getItems());
                Log.d("repo", "fetched new search results");
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

        return searchedVolumes;
    }

    public MutableLiveData<List<Item>> getLastFiveRecentSearches() {

        recentlySearchedDao.lastFiveRecentSearches()
                .flatMap(recentlySearchedBooks -> {
                    return Observable.fromIterable(recentlySearchedBooks)
                            .map(recentlySearchedBook -> {
                                return recentlySearchedBook.getItem();
                            })
                            .toList()
                            .toObservable();

                })
                .doOnNext(items -> recentSearches.postValue(items))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e("repo", throwable.getMessage()))
                .subscribe();

        return recentSearches;
    }

    @SuppressLint("CheckResult")
    public void saveBookToLocal(Item item) {
        Single.concat(bookDao.saveBook(item), recentlySearchedDao.createNewRecentSearch(new RecentlySearchedBook().createItem(item)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> Log.d("book inserted", String.valueOf(item.getVolumeInfo().getId())));
    }

    public void saveBookToCurrentlyReading(String bookId) {
        CurrentlyReadingStats stats = new CurrentlyReadingStats();
        stats.setStatsId(bookId);
        stats.setReadStatus(CurrentlyReadingStats.READING);
        stats.setStartedReading(new Date());
        stats.setLastUpdated(new Date());
        currentlyReadingDao.createNewReadingStats(stats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .doOnError(throwable -> Log.e("Repo currently", throwable.getMessage()))
                .subscribe();
    }

    public void deleteCurrentlyReadingStats(CurrentlyReadingStats stats) {
        deleteDisposable = Observable.timer(3, TimeUnit.SECONDS)
                .flatMap(aLong -> {
                    return
                            currentlyReadingDao.deleteCurrentlyReadingStats(stats)
                                    .doOnSuccess(id -> Log.d("DELETED", String.valueOf(id)))
                                    .doOnError(throwable -> Log.e("Repo", throwable.getMessage())).toObservable();
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .subscribe();
    }

    public void markAsRead(CurrentlyReadingStats stats) {
        stats.setReadStatus(CurrentlyReadingStats.READ);
        readDisposable = Observable.timer(3, TimeUnit.SECONDS)
                .flatMap(aLong -> {
                    return
                            currentlyReadingDao.markAsRead(stats)
                                    .doOnSuccess(id -> Log.d("MARKED READ", String.valueOf(id)))
                                    .doOnError(throwable -> Log.e("Repo", throwable.getMessage())).toObservable();
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .subscribe();
    }

    public void updateBookProgress(CurrentlyReadingStats stats, int progress) {
        stats.setLastUpdated(new Date());
        stats.setReadProgress(progress);

        currentlyReadingDao.createNewReadingStats(stats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .doOnError(throwable -> Log.e("Repo currently", throwable.getMessage()))
                .subscribe();
    }

    public MutableLiveData<List<BookWithStats>> getAllBooksWithStats() {
        currentlyReadingDao.allBookWithStats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(sub -> disposables.add(sub))
                .doOnError(throwable -> Log.e("fetching current error", throwable.getMessage()))
                .doOnNext(item -> booksWithStats.postValue(item))
                .subscribe();

        return booksWithStats;
    }

    public void undoDelete() {
        if (!deleteDisposable.isDisposed()) {
            deleteDisposable.dispose();
        }
    }

    public void undoMarkAsRead() {
        if (!readDisposable.isDisposed()) {
            readDisposable.dispose();
        }
    }


    public void disposeObservers() {
        disposables.clear();
    }

}