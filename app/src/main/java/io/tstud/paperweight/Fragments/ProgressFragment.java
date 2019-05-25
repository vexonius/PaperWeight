package io.tstud.paperweight.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.tstud.paperweight.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends Fragment {

    private ImageView mCover;

    public ProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_progress, container, false);

        mCover = (ImageView)v.findViewById(R.id.progress_cover);

        Glide.with(getActivity())
                .load(R.drawable.lotr)
                .apply(RequestOptions.fitCenterTransform())
                .into(mCover);


        return v;
    }

}
