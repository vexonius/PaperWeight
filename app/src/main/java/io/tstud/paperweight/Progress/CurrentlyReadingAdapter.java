package io.tstud.paperweight.Progress;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.chip.Chip;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.List;

import io.tstud.paperweight.Model.Models.BookWithStats;
import io.tstud.paperweight.R;
import io.tstud.paperweight.Utils.CurrentlyReadingClickListener;


public class CurrentlyReadingAdapter extends RecyclerView.Adapter<CurrentlyReadingAdapter.CurrentlyReadingViewHolder> {
    List<BookWithStats> booksAndStats;

    private CurrentlyReadingClickListener mReadingClickListener;

    public CurrentlyReadingAdapter(List<BookWithStats> bookWithStats, CurrentlyReadingClickListener readingClickListener) {
        this.booksAndStats = bookWithStats;
        this.mReadingClickListener = readingClickListener;
    }

    public class CurrentlyReadingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, author, progress;
        ImageView cover;
        Chip markAsRead, updateProgress;
        CurrentlyReadingClickListener readingClickListener;
        CircularProgressBar circularProgressBar;
        public ConstraintLayout foreground, background;

        public CurrentlyReadingViewHolder(@NonNull View itemView, CurrentlyReadingClickListener readingClickListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.cr_title);
            author = (TextView) itemView.findViewById(R.id.cr_author);
            progress = (TextView) itemView.findViewById(R.id.cr_progress);
            cover = (ImageView) itemView.findViewById(R.id.cr_cover);
            markAsRead = (Chip) itemView.findViewById(R.id.mark_as_read_chip);
            updateProgress = (Chip) itemView.findViewById(R.id.update_chip);
            circularProgressBar = (CircularProgressBar)itemView.findViewById(R.id.circular_progress);
            foreground = (ConstraintLayout)itemView.findViewById(R.id.item_content);

            this.readingClickListener = readingClickListener;
            itemView.setOnClickListener(this);

            updateProgress.setOnClickListener(view -> readingClickListener.onUpdateProgressClick(getAdapterPosition(), booksAndStats.get(getAdapterPosition())));
            markAsRead.setOnClickListener(view -> readingClickListener.onMarkReadClick(getAdapterPosition(), booksAndStats.get(getAdapterPosition()).getItem()));
        }

        @Override
        public void onClick(View view) {
            readingClickListener.onClick(getAdapterPosition(), cover, booksAndStats.get(getAdapterPosition()).getItem());
        }
    }


    @NonNull
    @Override
    public CurrentlyReadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currently_reading_list_item, parent, false);
        return new CurrentlyReadingViewHolder(view, mReadingClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentlyReadingViewHolder holder, int position) {
        BookWithStats bookAndStats = booksAndStats.get(position);
        holder.title.setText(bookAndStats.getItem().getVolumeInfo().getTitle());
        holder.author.setText(bookAndStats.getItem().getVolumeInfo().getAuthors().get(0));
        holder.progress.setText(String.valueOf(bookAndStats.getStats().getReadProgress()));
        holder.circularProgressBar.setProgress(bookAndStats.getStats().getReadProgress());

        if (bookAndStats.getItem().getVolumeInfo().getImageLinks() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(bookAndStats.getItem().getVolumeInfo().getImageLinks().getSmallThumbnailWithoutCurledEdge())
                    .transforms(new CenterCrop(), new RoundedCorners(30))
                    .placeholder(new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), R.color.dirty_white)))
                    .into(holder.cover);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), R.color.dirty_white)))
                    .transforms(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.cover);
        }
    }

    public void updateData(List<BookWithStats> list) {
        this.booksAndStats = list;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        booksAndStats.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        if (booksAndStats != null)
            return booksAndStats.size();

        return 0;
    }
}
