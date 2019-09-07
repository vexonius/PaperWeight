package io.tstud.paperweight.Model.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.tstud.paperweight.Model.Models.BookWithStats;
import io.tstud.paperweight.Model.Models.CurrentlyReadingStats;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface CurrentlyReadingDao {

    @Query("SELECT * FROM currently_reading_stats INNER JOIN item ON currently_reading_stats.statsId = item.id")
    Observable<List<BookWithStats>> allBookWithStats();

    @Insert(onConflict = REPLACE)
    Single<Long> createNewReadingStats(CurrentlyReadingStats stats);

    @Delete
    Single<Integer> deleteCurrentlyReadingStats(CurrentlyReadingStats stats);

}
