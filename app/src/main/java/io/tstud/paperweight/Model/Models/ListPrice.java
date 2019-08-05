
package io.tstud.paperweight.Model.Models;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "list_price")
public class ListPrice {

    @SerializedName("amount")
    @Expose
    private Double priceAmount;
    @SerializedName("currencyCode")
    @Expose
    private String priceCurrencyCode;

    public Double getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(Double priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String getPriceCurrencyCode() {
        return priceCurrencyCode;
    }

    public void setPriceCurrencyCode(String priceCurrencyCode) {
        this.priceCurrencyCode = priceCurrencyCode;
    }

}
