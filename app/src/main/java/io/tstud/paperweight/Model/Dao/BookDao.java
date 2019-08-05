package io.tstud.paperweight.Model.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.tstud.paperweight.Model.Models.Item;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BookDao {

    @Insert(onConflict = REPLACE)
    Single<Long> saveBook(Item book);

    @Query("SELECT * FROM item WHERE id = :id ")
    Maybe<Item> getBookFromLocal(String id);

}
