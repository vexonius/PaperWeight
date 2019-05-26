package io.tstud.paperweight.Repo;

import android.util.Log;

import androidx.annotation.NonNull;

import io.tstud.paperweight.Model.Item;
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

    public Repository() {
        apiClient = RetrofitClient.getInstance();
        apiService = apiClient.create(GoogleBooksService.class);
    }

    public void getVolumeById(@NonNull String id) {
        Call<Item> singleBookCall = apiService.getVolumeById(id);
        singleBookCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Log.d("Book title: ", response.body().getVolumeInfo().getDescription());
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });

    }

}
