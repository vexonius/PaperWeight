package io.tstud.paperweight.Model.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import io.tstud.paperweight.Model.Local.CustomTypeConverters;

@Entity(tableName = "currently_reading_stats")
public class CurrentlyReadingStats {

    @PrimaryKey
    @NonNull
    private String statsId;

    @ColumnInfo(name = "progress")
    private int readProgress = 0;

    @ColumnInfo(name = "page_progress")
    private int pageProgress = 0;

    @ColumnInfo(name = "started_reading")
    @TypeConverters(CustomTypeConverters.class)
    private Date startedReading;

    @ColumnInfo(name = "last_updated")
    @TypeConverters(CustomTypeConverters.class)
    private Date lastUpdated;

    @ColumnInfo(name = "reading_speed")
    private int averageReadingSpeed;

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Date getStartedReading() {
        return startedReading;
    }

    public int getAverageReadingSpeed() {
        return averageReadingSpeed;
    }

    public int getPageProgress() {
        return pageProgress;
    }

    public int getReadProgress() {
        return readProgress;
    }

    @NonNull
    public String getStatsId() {
        return statsId;
    }

    public void setAverageReadingSpeed(int averageReadingSpeed) {
        this.averageReadingSpeed = averageReadingSpeed;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setPageProgress(int pageProgress) {
        this.pageProgress = pageProgress;
    }

    public void setReadProgress(int readProgress) {
        this.readProgress = readProgress;
    }

    public void setStartedReading(Date startedReading) {
        this.startedReading = startedReading;
    }

    public void setStatsId(@NonNull String statsId) {
        this.statsId = statsId;
    }
}
