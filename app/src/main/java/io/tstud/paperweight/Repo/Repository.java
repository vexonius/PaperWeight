package io.tstud.paperweight.Repo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import io.tstud.paperweight.Model.Collection;
import io.tstud.paperweight.Model.Item;
import io.tstud.paperweight.Model.VolumeInfo;
import io.tstud.paperweight.Retrofit.GoogleBooksService;
import io.tstud.paperweight.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by etino7 on 26/05/2019.
 */
public class Repository {

    private static Repository instance;
    private Retrofit apiClient;
    private GoogleBooksService apiService;
    private MutableLiveData<VolumeInfo> bookItem = new MutableLiveData<>();
    private MutableLiveData<Collection> collectionTrending = new MutableLiveData<>();
    private MutableLiveData<Collection> searchedVolumes = new MutableLiveData<>();


    private Repository() {
        apiClient = RetrofitClient.getInstance();
        apiService = apiClient.create(GoogleBooksService.class);
    }

    public static Repository getInstance(){
        if(instance == null)
            instance = new Repository();

        return instance;
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

            }
        });

        return searchedVolumes;
    }

}
