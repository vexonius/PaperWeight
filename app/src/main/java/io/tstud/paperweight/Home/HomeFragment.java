package io.tstud.paperweight.Home;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import io.tstud.paperweight.Model.VolumeInfo;
import io.tstud.paperweight.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    TextView tx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_home, container, false);

        tx = v.findViewById(R.id.trend);

        ImageView banner = (ImageView)v.findViewById(R.id.photo_id);

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.makeSingleBookRequest().observe(this, new Observer<VolumeInfo>() {
            @Override
            public void onChanged(@Nullable VolumeInfo info) {
                tx.setText(info.getCategories().get(1));
            }
        });

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();


        Glide.with(this)
                .load("https://images.unsplash.com/photo-1547104442-f5fc9a178155?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80")
                .transition(withCrossFade(factory))
                .transforms(new CenterCrop(), new RoundedCorners(50))
                .placeholder(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.dirty_white)))
                .into(banner);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

    }

}
