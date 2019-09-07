package io.tstud.paperweight.Utils;

import io.tstud.paperweight.Model.Models.BookWithStats;
import io.tstud.paperweight.Model.Models.Item;

/**
 * Created by etino7 on 06/09/2019.
 */
public interface CurrentlyReadingClickListener extends BookClickListener {

    void onUpdateProgressClick(int position, BookWithStats bookAndStats);

    void onMarkReadClick(int position, Item item);

    void onSwiped(int position);
}
