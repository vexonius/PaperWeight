package io.tstud.paperweight.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.tstud.paperweight.R;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView banner = (ImageView)v.findViewById(R.id.photo_id);

        Glide.with(getActivity())
                .asDrawable()
                .load(R.drawable.summer_reads)
                .apply(centerCropTransform())
                .into(banner);


        return v;
    }

}
