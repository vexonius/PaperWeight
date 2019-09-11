package io.tstud.paperweight.Progress;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import io.tstud.paperweight.BookDetail.BookDetailActivity;
import io.tstud.paperweight.Model.Models.BookWithStats;
import io.tstud.paperweight.Model.Models.Item;
import io.tstud.paperweight.R;
import io.tstud.paperweight.Utils.BottomSheet;
import io.tstud.paperweight.Utils.CurrentlyReadingClickListener;
import io.tstud.paperweight.Utils.SwipeToDeleteCallback;
import io.tstud.paperweight.Utils.SwipeToMarkReadCallback;


public class ProgressFragment extends Fragment implements CurrentlyReadingClickListener {

    private ImageView mCover;
    private RecyclerView recyclerView;
    private CurrentlyReadingAdapter adapter;
    View thisView;

    private ProgressViewModel viewModel;

    private final static String BOOK_ITEM = "BOOK_INFO";


    public static ProgressFragment newInstance() {
        return new ProgressFragment();
    }

    public ProgressFragment() {
        // Required empty constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_progress, container, false);
        thisView = v;

        recyclerView = (RecyclerView) v.findViewById(R.id.progress_recycler);

        ItemTouchHelper.SimpleCallback swipeToDeleteCallback = new SwipeToDeleteCallback(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(recyclerView);

        ItemTouchHelper.SimpleCallback swipeToMarkReadCallback = new SwipeToMarkReadCallback(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(swipeToMarkReadCallback).attachToRecyclerView(recyclerView);

        viewModel = ViewModelProviders.of(getActivity()).get(ProgressViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new CurrentlyReadingAdapter(null, this);
        recyclerView.setAdapter(adapter);


        viewModel.getData().observe(getActivity(), collection -> {
            if (collection != null)
                adapter.updateData(collection);
        });


        return v;
    }

    @Override
    public void onUpdateProgressClick(int position, BookWithStats item) {
        viewModel.setBookToUpdate(item);
        BottomSheet bottomSheet = BottomSheet.newInstance();
        bottomSheet.show(getActivity().getSupportFragmentManager(), "bottomsheet");
    }

    @Override
    public void onMarked(int position) {
        BookWithStats bookWithStats = adapter.booksAndStats.get(position);
        viewModel.markAsRead(bookWithStats.getStats());

        Snackbar snack = Snackbar.make(thisView, bookWithStats.getItem().getVolumeInfo().getTitle() + " marked read", 3000);
        snack.setAction("UNDO", view -> {
            viewModel.undoMarkAsRead();
            adapter.insertItem(position, bookWithStats);
        });
        snack.setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        snack.show();
        adapter.removeItem(position);
    }

    @Override
    public void onSwiped(int position) {
        BookWithStats bookWithStats = adapter.booksAndStats.get(position);
        viewModel.deleteCurrentlyReadingStats(bookWithStats.getStats());

        Snackbar snack = Snackbar.make(thisView, bookWithStats.getItem().getVolumeInfo().getTitle() + " deleted", 3000);
        snack.setAction("UNDO", view -> {
            viewModel.undoDelete();
            adapter.insertItem(position, bookWithStats);
        });
        snack.setActionTextColor(ContextCompat.getColor(getContext(), R.color.redish));
        snack.show();
        adapter.removeItem(position);
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
