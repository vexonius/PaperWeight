package io.tstud.paperweight.Model;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
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
        setErrorHandler();
        apiClient = RetrofitClient.getInstance();
        apiService = apiClient.create(GoogleBooksService.class);
        db = Database.getInstance();
        bookDao = db.bookDao();
        currentlyReadingDao = db.currentlyReadingDao();
        recentlySearchedDao = db.recentlySearchedDao();
    }

    private void setErrorHandler() {
        RxJavaPlugins.setErrorHandler(throwable -> {
            Log.e("APP REPO ERROR", "");
            throwable.printStackTrace();
        });
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
                .doOnError(throwable -> Log.e("Error while fetching", throwable.getMessage()))
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


    public Maybe<Collection> getTrendingList() {

       return apiService.getToReadList();

    }

    public MutableLiveData<List<Item>> getSearchVolumes(String query) {

        apiService.getVolumes(query, "relevance")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .doOnError(throwable -> Log.e("repo", throwable.toString()))
                .doOnSuccess(collection -> {
                    searchedVolumes.postValue(collection.getItems());
                    Log.d("repo", "fetched new search results");
                })
                .subscribe();

        return searchedVolumes;
    }

    public MutableLiveData<List<Item>> getLastFiveRecentSearches() {

        recentlySearchedDao.lastFiveRecentSearches()
                .flatMap(recentlySearchedBooks -> Observable.fromIterable(recentlySearchedBooks)
                        .map(RecentlySearchedBook::getItem)
                        .toList()
                        .toObservable())
                .doOnNext(items -> recentSearches.postValue(items))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e("repo", throwable.getMessage()))
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .subscribe();

        return recentSearches;
    }

    @SuppressLint("CheckResult")
    public void saveBookToLocal(Item item) {
        Single.concat(bookDao.saveBook(item), recentlySearchedDao.createNewRecentSearch(new RecentlySearchedBook().createItem(item)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> Log.d("book inserted", String.valueOf(item.getVolumeInfo().getId())),
                        throwable -> Log.e("Saving error", throwable.getMessage()));
    }

    public void saveBookToCurrentlyReading(String bookId) {

        Single.just(bookId)
                .flatMap(s -> {
                    CurrentlyReadingStats stats = new CurrentlyReadingStats();
                    stats.setStatsId(bookId);
                    stats.setReadStatus(CurrentlyReadingStats.READING);
                    stats.setStartedReading(new Date());
                    stats.setLastUpdated(new Date());
                    return currentlyReadingDao.createNewReadingStats(stats);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .doOnError(throwable -> Log.e("Repo currently", throwable.getMessage()))
                .subscribe();
    }

    public void deleteCurrentlyReadingStats(CurrentlyReadingStats stats) {
        deleteDisposable = Observable.timer(3, TimeUnit.SECONDS)
                .flatMap(aLong -> currentlyReadingDao.deleteCurrentlyReadingStats(stats)
                        .doOnSuccess(id -> Log.d("DELETED", String.valueOf(id)))
                        .doOnError(throwable -> Log.e("Repo", throwable.getMessage())).toObservable()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .subscribe();
    }

    public void markAsRead(CurrentlyReadingStats stats) {
        stats.setReadStatus(CurrentlyReadingStats.READ);
        readDisposable = Observable.timer(3, TimeUnit.SECONDS)
                .flatMap(aLong -> currentlyReadingDao.markAsRead(stats)
                        .doOnSuccess(id -> Log.d("MARKED READ", stats.getStatsId()))
                        .doOnError(throwable -> Log.e("Repo", throwable.getMessage())).toObservable()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> disposables.add(disposable))
                .subscribe();
    }

    public void updateBookProgress(CurrentlyReadingStats stats, int progress) {
        stats.setLastUpdated(new Date());
        stats.setReadProgress(progress);
        // TODO: write a method for calculating page progress from reading progress

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