package io.tstud.paperweight.Utils;

import android.widget.ImageView;

import io.tstud.paperweight.Model.Models.Item;

public interface BookClickListener {
    void onClick(int position, ImageView view, Item item);
}
