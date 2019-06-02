package io.tstud.paperweight.Fragments;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import io.tstud.paperweight.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends Fragment {

    private ImageView mCover;

    public static ProgressFragment newInstance(){
        return new ProgressFragment();
    }

    private ProgressFragment() {
        // Required empty constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_progress, container, false);

        mCover = (ImageView)v.findViewById(R.id.progress_cover);

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        Glide.with(getActivity())
                .load(R.drawable.lotr)
                .transition(withCrossFade(factory))
                .centerCrop()
                .transforms(new RoundedCorners(50))
                .placeholder(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.dirty_white)))
                .into(mCover);


        return v;
    }

}
