package io.tstud.paperweight.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by etino7 on 02/08/2018.
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://www.googleapis.com";


    public static Retrofit getRetrofitInstance(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
