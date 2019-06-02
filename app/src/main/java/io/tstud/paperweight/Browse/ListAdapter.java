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
    private TextView title, author;
    private ImageView cover;


    public ListAdapter(List<Item> volumes) {
        this.volumes = volumes;
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.carouselTitle);
            author = (TextView) itemView.findViewById(R.id.carouselAuthor);
            cover = (ImageView) itemView.findViewById(R.id.carouselCover);
        }
    }

    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        Item volume = volumes.get(position);
        if (volume.getVolumeInfo().getTitle() != null)
            title.setText(volume.getVolumeInfo().getTitle());
        if (volume.getVolumeInfo().getAuthors() != null)
            author.setText(volume.getVolumeInfo().getAuthors().get(0));

        if (volume.getVolumeInfo().getImageLinks() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(volume.getVolumeInfo().getImageLinks().getSmallThumbnail().replace("&zoom=5&edge=curl", ""))
                    .transforms(new CenterCrop(), new RoundedCorners(50))
                    .placeholder(new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), R.color.dirty_white)))
                    .into(cover);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), R.color.dirty_white)))
                    .transforms(new CenterCrop(), new RoundedCorners(50))
                    .into(cover);
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
