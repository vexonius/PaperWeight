package io.tstud.paperweight.Browse;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.idlestar.ratingstar.RatingStarView;

import java.util.List;

import io.tstud.paperweight.Model.Models.Item;
import io.tstud.paperweight.R;
import io.tstud.paperweight.Utils.BookClickListener;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CarouselViewHolder> {
    private List<Item> volumes;

    private BookClickListener mBookClickListener;


    public ListAdapter(List<Item> volumes, BookClickListener bookClickListener) {
        this.volumes = volumes;
        this.mBookClickListener = bookClickListener;
    }


    public class CarouselViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, author;
        private ImageView cover;
        private RatingStarView stars;
        BookClickListener bookClickListener;

        public CarouselViewHolder(@NonNull View itemView, BookClickListener bookClickListener) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listTitle);
            author = (TextView) itemView.findViewById(R.id.listAuthor);
            cover = (ImageView) itemView.findViewById(R.id.listCover);
            stars = (RatingStarView) itemView.findViewById(R.id.stars);
            this.bookClickListener = bookClickListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            bookClickListener.onClick(getAdapterPosition(), cover, volumes.get(getAdapterPosition()));
        }
    }

    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CarouselViewHolder(view, mBookClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        Item volume = volumes.get(position);
        float rating = volume.getVolumeInfo().getAverageRating().floatValue();

        if (volume.getVolumeInfo().getTitle() != null)
            holder.title.setText(volume.getVolumeInfo().getTitle());
        if (volume.getVolumeInfo().getAuthors() != null)
            holder.author.setText(volume.getVolumeInfo().getAuthors().get(0));

        holder.stars.setRating((int)rating);

        if (volume.getVolumeInfo().getImageLinks() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(volume.getVolumeInfo().getImageLinks().getSmallThumbnailWithoutCurledEdge())
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .placeholder(new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), R.color.dirty_white)))
                    .into(holder.cover);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), R.color.dirty_white)))
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.cover);
        }

    }

    public void updateData(List<Item> list) {
        this.volumes = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (volumes != null)
            return volumes.size();
        else
            return 0;
    }
}
