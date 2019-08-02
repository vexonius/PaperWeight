
package io.tstud.paperweight.Model;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "list_price")
public class ListPrice {

    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}
