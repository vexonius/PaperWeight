package io.tstud.paperweight.Home;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by etino7 on 30/05/2019.
 */
public class CarouselItemDecoration extends RecyclerView.ItemDecoration {

    private static int mOffsetPx = 40;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) > 0) {
            return;
        }

        outRect.left = mOffsetPx;
    }
}
