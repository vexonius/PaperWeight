
package io.tstud.paperweight.Model.Models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.tstud.paperweight.Model.Local.CustomTypeConverters;

@Entity(tableName = "sale_info")
public class SaleInfo {

    @SerializedName("country")
    @Expose
    private String countryOfSale;
    @SerializedName("saleability")
    @Expose
    private String saleability;
    @SerializedName("isEbook")
    @Expose
    private Boolean isEbook;
    @SerializedName("listPrice")
    @Expose
    @Embedded
    private ListPrice listPrice;
    @SerializedName("retailPrice")
    @Expose
    @Embedded
    private RetailPrice retailPrice;
    @SerializedName("buyLink")
    @Expose
    private String buyLink;
    @SerializedName("offers")
    @Expose
    @TypeConverters(CustomTypeConverters.class)
    private List<Offer> offers = null;

    public String getCountryOfSale() {
        return countryOfSale;
    }

    public void setCountryOfSale(String country) {
        this.countryOfSale = country;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public Boolean getIsEbook() {
        return isEbook;
    }

    public void setIsEbook(Boolean isEbook) {
        this.isEbook = isEbook;
    }

    public ListPrice getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPrice listPrice) {
        this.listPrice = listPrice;
    }

    public RetailPrice getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(RetailPrice retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

}
