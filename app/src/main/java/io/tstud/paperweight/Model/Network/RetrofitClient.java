package io.tstud.paperweight.Model.Network;

import io.reactivex.schedulers.Schedulers;
import io.tstud.paperweight.Utils.NoNetworkInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by etino7 on 02/08/2018.
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://www.googleapis.com";


    public static Retrofit getInstance(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(provideClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.
                        createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static OkHttpClient provideClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        return builder.addInterceptor(new NoNetworkInterceptor())
                .build();
    }
}
