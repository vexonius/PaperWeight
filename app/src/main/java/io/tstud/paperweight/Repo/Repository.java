package io.tstud.paperweight.Repo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.tstud.paperweight.Model.Collection;
import io.tstud.paperweight.Model.Item;
import io.tstud.paperweight.Model.VolumeInfo;
import io.tstud.paperweight.Retrofit.GoogleBooksService;
import io.tstud.paperweight.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Repository {

    private static Repository instance;
    private Retrofit apiClient;
    private GoogleBooksService apiService;
    private MutableLiveData<VolumeInfo> bookItem = new MutableLiveData<>();
    private MutableLiveData<Collection> collectionTrending = new MutableLiveData<>();
    private MutableLiveData<Collection> searchedVolumes = new MutableLiveData<>();
    private MutableLiveData<Collection> book = new MutableLiveData<>();
    private CompositeDisposable disposables = new CompositeDisposable();


    private Repository() {
        apiClient = RetrofitClient.getInstance();
        apiService = apiClient.create(GoogleBooksService.class);
       // getSingleTest();
    }

    public static Repository getInstance(){
        if(instance == null)
            instance = new Repository();

        return instance;
    }

    public MutableLiveData<VolumeInfo> getSingleTest(){
        apiService.getSingleVolumeById("rL99E5lSM0wC")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Item>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(Item item) {
                        bookItem.setValue(item.getVolumeInfo());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        return bookItem;
    }

    public MutableLiveData<VolumeInfo> getVolumeById(@NonNull String id) {

        apiService.getVolumeById(id).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                bookItem.postValue(response.body().getVolumeInfo());
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });

        return bookItem;
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

    public void disposeObservers(){
        disposables.clear();
    }

}