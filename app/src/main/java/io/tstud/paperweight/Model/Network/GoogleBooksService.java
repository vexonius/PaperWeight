package io.tstud.paperweight.Model.Network;

import io.reactivex.Single;
import io.tstud.paperweight.Model.Models.Collection;
import io.tstud.paperweight.Model.Models.Item;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by etino7 on 02/08/2018.
 */
public interface GoogleBooksService {

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json"})
    @GET("/books/v1/volumes")
    Call<Collection> getVolumes();

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json"})
    @GET("/books/v1/volumes")
    Call<Collection> getVolumes(@Query("q") String query, @Query("orderBy") String order);

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json"})
    @GET("/books/v1/volumes")
    Call<Collection> getSearchResults(@Query("q") String query);

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json"})
    @GET("/books/v1/volumes/{volumeId}")
    Call<Item> getVolumeById(@Path("volumeId") String volumeId);

    @Headers({"Content-Type: application/json;charset=utf-8", "Accept: application/json"})
    @GET("/books/v1/volumes/{volumeId}")
    Single<Item> getSingleVolumeById(@Path("volumeId") String volumeId);

}
