package io.tstud.paperweight.Repo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    private Retrofit apiClient;
    private GoogleBooksService apiService;
    private MutableLiveData<VolumeInfo> bookItem = new MutableLiveData<>();

    public Repository() {
        apiClient = RetrofitClient.getInstance();
        apiService = apiClient.create(GoogleBooksService.class);
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

}
