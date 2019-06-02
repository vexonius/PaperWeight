package io.tstud.paperweight.Browse;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Timer;
import java.util.TimerTask;

import io.tstud.paperweight.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {
    private EditText searchbar;
    private BrowseViewModel viewModel;
    private RecyclerView recyclerResults;
    private ListAdapter listAdapter;
    private HorizontalScrollView chipScrollView;
    private Timer timer;

    public static BrowseFragment newInstance(){
        return new BrowseFragment();
    }

    public BrowseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browse, container, false);

        searchbar = (EditText) v.findViewById(R.id.searchbar);
        recyclerResults = (RecyclerView) v.findViewById(R.id.categoriesRecycler);
        chipScrollView = (HorizontalScrollView) v.findViewById(R.id.chip_scroll);

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
        listAdapter = new ListAdapter(null);
        recyclerResults.setAdapter(listAdapter);


        viewModel.getSearchResults().observe(getViewLifecycleOwner(), collection -> {
            if(collection!=null)
                listAdapter.updateData(collection.getItems());
        });

        return v;
    }

}
