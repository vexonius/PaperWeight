package io.tstud.paperweight.Home;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import io.tstud.paperweight.Model.Models.Collection;
import io.tstud.paperweight.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private TextView tx, tx1;
    private RecyclerView recyclerTrending, recyclerFantasy;
    private CarouselAdapter ca, ca1;
    private SwipeRefreshLayout swipeRefreshLayout;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        tx = v.findViewById(R.id.trend);
        recyclerTrending = (RecyclerView) v.findViewById(R.id.trend_recycler);

        tx1 = v.findViewById(R.id.recommend);
        recyclerFantasy = (RecyclerView) v.findViewById(R.id.fantasyRecycler);

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.homeSwipeLayout);

        ImageView banner = (ImageView) v.findViewById(R.id.photo_id);

        viewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);


    /*    viewModel.makeSingleBookRequest().observe(this, new Observer<VolumeInfo>() {
            @Override
            public void onChanged(@Nullable VolumeInfo info) {
                tx.setText(info.getCategories().get(1));
            }
        });  */

        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();


        Glide.with(this)
                .load("https://images.unsplash.com/photo-1456953180671-730de08edaa7?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1502&q=80")
                .transition(withCrossFade(factory))
                .transform(new CenterCrop(), new RoundedCorners(50))
                .placeholder(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.dirty_white)))
                .into(banner);


        recyclerTrending.setLayoutManager(new LinearLayoutManager(recyclerTrending.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerTrending.addItemDecoration(new CarouselItemDecoration());
        ca = new CarouselAdapter(null);
        recyclerTrending.setAdapter(ca);


        viewModel.getTrendingBooks().observe(getViewLifecycleOwner(), new Observer<Collection>() {
            @Override
            public void onChanged(Collection collection) {
                ca.updateData(collection.getItems());
                tx.setText(collection.getItems().get(0).getVolumeInfo().getCategories().get(0));
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerFantasy.setLayoutManager(new LinearLayoutManager(recyclerFantasy.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerFantasy.addItemDecoration(new CarouselItemDecoration());
        ca1 = new CarouselAdapter(null);
        recyclerFantasy.setAdapter(ca1);

        viewModel.getTrendingBooks().observe(getViewLifecycleOwner(), new Observer<Collection>() {
            @Override
            public void onChanged(Collection collection) {
                ca1.updateData(collection.getItems());
                tx1.setText(collection.getItems().get(0).getVolumeInfo().getCategories().get(0));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.updateData();
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
