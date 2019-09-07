package io.tstud.paperweight.Utils;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import io.tstud.paperweight.Progress.CurrentlyReadingAdapter;

/**
 * Created by etino7 on 07/09/2019.
 */
public class SwipeToDeleteHelper extends ItemTouchHelper.SimpleCallback {
    private CurrentlyReadingClickListener listener;

    public SwipeToDeleteHelper(int dragDirs, int swipeDirs, CurrentlyReadingClickListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        getDefaultUIUtil().onDraw(c, recyclerView,
                ((CurrentlyReadingAdapter.CurrentlyReadingViewHolder) viewHolder).foreground, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        getDefaultUIUtil().onDrawOver(c, recyclerView,
                ((CurrentlyReadingAdapter.CurrentlyReadingViewHolder) viewHolder).foreground, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        getDefaultUIUtil().clearView(((CurrentlyReadingAdapter.CurrentlyReadingViewHolder) viewHolder).foreground);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            getDefaultUIUtil().onSelected(((CurrentlyReadingAdapter.CurrentlyReadingViewHolder) viewHolder).foreground);
        }
    }
}
