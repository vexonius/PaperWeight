
package io.tstud.paperweight.Model.Models;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "pdf")
public class Pdf {

    @SerializedName("isAvailable")
    @Expose
    private Boolean isPdfAvailable;
    @SerializedName("acsTokenLink")
    @Expose
    private String pdfAcsTokenLink;

    public Boolean getIsPdfAvailable() {
        return isPdfAvailable;
    }

    public void setIsPdfAvailable(Boolean isPdfAvailable) {
        this.isPdfAvailable = isPdfAvailable;
    }

    public String getPdfAcsTokenLink() {
        return pdfAcsTokenLink;
    }

    public void setPdfAcsTokenLink(String pdfAcsTokenLink) {
        this.pdfAcsTokenLink = pdfAcsTokenLink;
    }

}
