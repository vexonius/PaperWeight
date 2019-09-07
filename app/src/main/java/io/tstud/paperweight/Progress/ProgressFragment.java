package io.tstud.paperweight.Progress;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.tstud.paperweight.BookDetail.BookDetailActivity;
import io.tstud.paperweight.Model.Models.BookWithStats;
import io.tstud.paperweight.Model.Models.Item;
import io.tstud.paperweight.R;
import io.tstud.paperweight.Utils.BottomSheet;
import io.tstud.paperweight.Utils.CurrentlyReadingClickListener;


public class ProgressFragment extends Fragment implements CurrentlyReadingClickListener {

    private ImageView mCover;
    private RecyclerView recyclerView;
    private CurrentlyReadingAdapter adapter;

    private ProgressViewModel viewModel;

    private final static String BOOK_ITEM = "BOOK_INFO";


    public static ProgressFragment newInstance() {
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

        recyclerView = (RecyclerView) v.findViewById(R.id.progress_recycler);

        viewModel = ViewModelProviders.of(getActivity()).get(ProgressViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new CurrentlyReadingAdapter(null, this);
        recyclerView.setAdapter(adapter);


        viewModel.getData().observe(getViewLifecycleOwner(), collection -> {
            if (collection != null)
                adapter.updateData(collection);
        });


        return v;
    }

    @Override
    public void onUpdateProgressClick(int position, BookWithStats item) {
        viewModel.setBookToUpdate(item);
        BottomSheet bottomSheet = BottomSheet.newInstance();
        bottomSheet.show(getActivity().getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");

    }

    @Override
    public void onMarkReadClick(int position, Item item) {

    }

    @Override
    public void onClick(int position, ImageView view, Item item) {
        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
        intent.putExtra(BOOK_ITEM, item.getVolumeInfo().getImageLinks().getSmallThumbnailWithoutCurledEdge());
        intent.putExtra("book_id", item.getId());
        intent.putExtra("title", item.getVolumeInfo().getTitle());
        intent.putExtra("author", item.getVolumeInfo().getAuthors().get(0));
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), (View) view, "bookCover");
        startActivity(intent, options.toBundle());
    }
}
