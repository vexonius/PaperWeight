package io.tstud.paperweight.Retrofit;

import io.tstud.paperweight.Model.Collection;
import io.tstud.paperweight.Model.Item;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by etino7 on 02/08/2018.
 */
public interface GoogleBooksService {

    @GET("/books/v1/volumes")
    Call<Collection> getVolumes();

    @GET("/books/v1/volumes/{volumeId}")
    Call<Item> getVolumeById(@Path("volumeId") String volumeId);

}
