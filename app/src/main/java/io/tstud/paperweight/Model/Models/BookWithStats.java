package io.tstud.paperweight.Model.Models;

import androidx.room.Embedded;


public class BookWithStats {

    @Embedded
    private Item item;

    @Embedded
    private CurrentlyReadingStats stats;

    public CurrentlyReadingStats getStats() {
        return stats;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setStats(CurrentlyReadingStats stats) {
        this.stats = stats;
    }
}
