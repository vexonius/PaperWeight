
package io.tstud.paperweight.Model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "item")
public class Item {

    @SerializedName("kind")
    @Expose
    @ColumnInfo(name = "kind")
    private String kind;

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;

    @SerializedName("etag")
    @Expose
    private String etag;

    @SerializedName("selfLink")
    @Expose
    private String selfLink;

    @SerializedName("volumeInfo")
    @Expose
    @Embedded
    private VolumeInfo volumeInfo;

    @SerializedName("saleInfo")
    @Expose
    @Embedded
    private SaleInfo saleInfo;

    @SerializedName("accessInfo")
    @Expose
    @Embedded
    private AccessInfo accessInfo;

    @SerializedName("searchInfo")
    @Expose
    @Embedded
    private SearchInfo searchInfo;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

}
