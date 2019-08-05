
package io.tstud.paperweight.Model.Models;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.tstud.paperweight.Model.Local.CustomTypeConverters;

@Entity(tableName = "series_info")
public class SeriesInfo {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("shortSeriesBookTitle")
    @Expose
    private String shortSeriesBookTitle;
    @SerializedName("bookDisplayNumber")
    @Expose
    private String bookDisplayNumber;
    @SerializedName("volumeSeries")
    @Expose
    @TypeConverters(CustomTypeConverters.class)
    private List<VolumeSeries> volumeSeries = null;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getShortSeriesBookTitle() {
        return shortSeriesBookTitle;
    }

    public void setShortSeriesBookTitle(String shortSeriesBookTitle) {
        this.shortSeriesBookTitle = shortSeriesBookTitle;
    }

    public String getBookDisplayNumber() {
        return bookDisplayNumber;
    }

    public void setBookDisplayNumber(String bookDisplayNumber) {
        this.bookDisplayNumber = bookDisplayNumber;
    }

    public List<VolumeSeries> getVolumeSeries() {
        return volumeSeries;
    }

    public void setVolumeSeries(List<VolumeSeries> volumeSeries) {
        this.volumeSeries = volumeSeries;
    }

}
