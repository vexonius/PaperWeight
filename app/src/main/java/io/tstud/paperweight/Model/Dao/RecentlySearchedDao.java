package io.tstud.paperweight.Model.Dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.tstud.paperweight.Model.Models.RecentlySearchedBook;

@Dao
public interface RecentlySearchedDao {


    // TODO: Fix this
    @Query("SELECT * FROM recently_searched_books LIMIT 5")
    Observable<List<RecentlySearchedBook>> lastFiveRecentSearches();

    @Query("SELECT * FROM recently_searched_books")
    Observable<List<RecentlySearchedBook>> allRecentSearches();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Single<Long> createNewRecentSearch(RecentlySearchedBook book);

}
