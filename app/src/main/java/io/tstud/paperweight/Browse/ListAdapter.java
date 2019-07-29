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

import java.util.List;

import io.tstud.paperweight.Model.Item;
import io.tstud.paperweight.R;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CarouselViewHolder> {
    private List<Item> volumes;

    private OnBookListener mOnBookListener;


    public ListAdapter(List<Item> volumes, OnBookListener onBookListener) {
        this.volumes = volumes;
        this.mOnBookListener = onBookListener;
    }


    public class CarouselViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private TextView title, author;
        private ImageView cover;
        OnBookListener onBookListener;

        public CarouselViewHolder(@NonNull View itemView, OnBookListener onBookListener) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listTitle);
            author = (TextView) itemView.findViewById(R.id.listAuthor);
            cover = (ImageView) itemView.findViewById(R.id.listCover);
            this.onBookListener = onBookListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onBookListener.onClick(getAdapterPosition(), cover, volumes.get(getAdapterPosition()));
        }
    }

    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CarouselViewHolder(view, mOnBookListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        Item volume = volumes.get(position);
        if (volume.getVolumeInfo().getTitle() != null)
            holder.title.setText(volume.getVolumeInfo().getTitle());
        if (volume.getVolumeInfo().getAuthors() != null)
            holder.author.setText(volume.getVolumeInfo().getAuthors().get(0));

        if (volume.getVolumeInfo().getImageLinks() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(volume.getVolumeInfo().getImageLinks().getSmallThumbnail().replace("&zoom=5&edge=curl", ""))
                    .transforms(new CenterCrop(), new RoundedCorners(50))
                    .placeholder(new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), R.color.dirty_white)))
                    .into(holder.cover);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), R.color.dirty_white)))
                    .transforms(new CenterCrop(), new RoundedCorners(50))
                    .into(holder.cover);
        }

    }

    public void updateData(List<Item> list) {
        this.volumes = list;
        notifyDataSetChanged();
    }

    public interface OnBookListener{
        void onClick(int position, ImageView view, Item item);
    }

    @Override
    public int getItemCount() {
        if (volumes != null)
            return volumes.size();
        else
            return 0;
    }
}
