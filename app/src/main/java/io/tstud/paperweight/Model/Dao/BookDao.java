package io.tstud.paperweight.Model.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Single;
import io.tstud.paperweight.Model.VolumeInfo;

@Dao
public interface BookDao {

    @Insert
    long saveBook(VolumeInfo book);

    @Query("SELECT * FROM books WHERE id = :id ")
    Single<VolumeInfo> getBookFromLocal(String id);

}
