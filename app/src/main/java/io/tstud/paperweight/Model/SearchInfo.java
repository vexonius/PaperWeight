
package io.tstud.paperweight.Model;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "search_info")
public class SearchInfo {

    @SerializedName("textSnippet")
    @Expose
    private String textSnippet;

    public String getTextSnippet() {
        return textSnippet;
    }

    public void setTextSnippet(String textSnippet) {
        this.textSnippet = textSnippet;
    }

}
