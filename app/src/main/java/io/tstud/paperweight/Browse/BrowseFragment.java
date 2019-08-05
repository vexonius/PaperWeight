package io.tstud.paperweight.Browse;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;

import java.util.Timer;
import java.util.TimerTask;

import io.tstud.paperweight.BookDetail.BookDetailActivity;
import io.tstud.paperweight.Model.Models.Item;
import io.tstud.paperweight.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment implements ListAdapter.OnBookListener {

    private final static String BOOK_ITEM = "BOOK_INFO";

    private EditText searchbar;
    private BrowseViewModel viewModel;
    private RecyclerView recyclerResults;
    private ListAdapter listAdapter;
    private ChipGroup chipScrollView;
    private Timer timer;

    public static BrowseFragment newInstance(){
        return new BrowseFragment();
    }

    public BrowseFragment() {
        // Required empty public constructor
    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browse, container, false);

        searchbar = (EditText) v.findViewById(R.id.searchbar);
        recyclerResults = (RecyclerView) v.findViewById(R.id.categoriesRecycler);
        chipScrollView = (ChipGroup) v.findViewById(R.id.chips);

        viewModel = ViewModelProviders.of(getActivity()).get(BrowseViewModel.class);

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                chipScrollView.setVisibility(View.GONE);

                // delay api calls for some time
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(searchbar.getText() != null && searchbar.getText().length() > 1)
                            viewModel.setSearchQuery(searchbar.getText().toString());
                    }
                }, 400);


            }
        });

        recyclerResults.setLayoutManager(new LinearLayoutManager(recyclerResults.getContext(), LinearLayoutManager.VERTICAL, false));
        listAdapter = new ListAdapter(null, this);
        recyclerResults.setAdapter(listAdapter);


        viewModel.getSearchResults().observe(getViewLifecycleOwner(), collection -> {
            if(collection!=null)
                listAdapter.updateData(collection.getItems());
        });

        return v;
    }

    @Override
    public void onClick(int position, ImageView view, Item item) {
        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
        intent.putExtra(BOOK_ITEM, item.getVolumeInfo().getImageLinks().getSmallThumbnailWithoutCurledEdge());
        intent.putExtra("book_id", item.getId());
        intent.putExtra("title", item.getVolumeInfo().getTitle());
        intent.putExtra("author", item.getVolumeInfo().getAuthors().get(0));
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), (View)view, "bookCover");
        startActivity(intent, options.toBundle());
    }
}
