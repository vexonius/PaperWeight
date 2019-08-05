package io.tstud.paperweight.Model;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.tstud.paperweight.Model.Dao.BookDao;
import io.tstud.paperweight.Model.Local.Database;
import io.tstud.paperweight.Model.Models.Collection;
import io.tstud.paperweight.Model.Models.Item;
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

    private MutableLiveData<Item> bookItem = new MutableLiveData<>();
    private MutableLiveData<Collection> collectionTrending = new MutableLiveData<>();
    private MutableLiveData<Collection> searchedVolumes = new MutableLiveData<>();

    private CompositeDisposable disposables = new CompositeDisposable();


    private Repository() {
        apiClient = RetrofitClient.getInstance();
        apiService = apiClient.create(GoogleBooksService.class);
        db = Database.getInstance();
        bookDao = db.bookDao();
    }

    public static synchronized Repository getInstance() {
        if (instance == null)
            instance = new Repository();

        return instance;
    }

    public MutableLiveData<Item> getBookDetails() {
        Observable.concat(getSingleLocalTest(), getSingleNetworkTest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .firstElement()
                .doOnSuccess(item -> bookItem.setValue(item))
                .subscribe();


        return bookItem;
    }

    public Observable<Item> getSingleNetworkTest() {
        return apiService.getSingleVolumeById(TEST_BOOK_ID)
                .doOnSuccess(item -> Log.d("Book fetched network", item.getId()))
                .toObservable();
    }

    public Observable<Item> getSingleLocalTest() {
        return db.bookDao().getBookFromLocal(TEST_BOOK_ID)
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

    public MutableLiveData<Collection> getSearchVolumes(String query) {

        apiService.getVolumes(query, "relevance").enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                searchedVolumes.postValue(response.body());
                Log.d("repo", "fetched new search results");
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

        return searchedVolumes;
    }

    @SuppressLint("CheckResult")
    public void saveBookToLocal(Item item) {
        bookDao.saveBook(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                            Log.d("Book inserted", item.getId());
                        },
                        throwable -> {
                            Log.e("Error", throwable.getMessage());
                        });
        Log.d("BOOK SAVED TO DB:", item.getId());
    }

    public void disposeObservers() {
        disposables.clear();
    }


}