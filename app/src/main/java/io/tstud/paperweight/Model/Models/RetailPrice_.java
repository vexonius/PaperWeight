
package io.tstud.paperweight.Model.Models;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "retail_price_")
public class RetailPrice_ {

    @SerializedName("amountInMicros")
    @Expose
    private Double amountInMicros;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;

    public Double getAmountInMicros() {
        return amountInMicros;
    }

    public void setAmountInMicros(Double amountInMicros) {
        this.amountInMicros = amountInMicros;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}
