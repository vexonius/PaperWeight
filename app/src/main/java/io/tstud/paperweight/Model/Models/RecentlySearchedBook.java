package io.tstud.paperweight.Model.Models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by etino7 on 12/09/2019.
 */

@Entity(tableName = "recently_searched_books")
public class RecentlySearchedBook {

    @PrimaryKey (autoGenerate = true)
    private int searchId;

    @Embedded
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public RecentlySearchedBook createItem(Item item) {
        this.item = item;
        return this;
    }

    public int getSearchId() {
        return searchId;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }
}
