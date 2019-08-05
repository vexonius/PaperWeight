
package io.tstud.paperweight.Model.Models;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "volume_series")
public class VolumeSeries {

    @SerializedName("seriesId")
    @Expose
    private String seriesId;
    @SerializedName("seriesBookType")
    @Expose
    private String seriesBookType;
    @SerializedName("orderNumber")
    @Expose
    private Integer orderNumber;

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesBookType() {
        return seriesBookType;
    }

    public void setSeriesBookType(String seriesBookType) {
        this.seriesBookType = seriesBookType;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

}
