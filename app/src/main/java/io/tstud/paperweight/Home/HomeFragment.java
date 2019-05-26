package io.tstud.paperweight.Home;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.tstud.paperweight.R;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView banner = (ImageView)v.findViewById(R.id.photo_id);

        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.makeSingleBookRequest();

        Glide.with(getActivity())
                .asDrawable()
                .load(R.drawable.summer_reads)
                .apply(centerCropTransform())
                .into(banner);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

    }

}
