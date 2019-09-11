package io.tstud.paperweight.Utils;

import io.tstud.paperweight.Model.Models.BookWithStats;

/**
 * Created by etino7 on 06/09/2019.
 */
public interface CurrentlyReadingClickListener extends BookClickListener {

    void onUpdateProgressClick(int position, BookWithStats bookAndStats);

    void onSwiped(int position);

    void onMarked(int position);
}
