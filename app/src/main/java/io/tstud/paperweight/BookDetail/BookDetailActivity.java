package io.tstud.paperweight.BookDetail;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.idlestar.ratingstar.RatingStarView;

import io.tstud.paperweight.R;

public class BookDetailActivity extends AppCompatActivity {

    private final static String BOOK_ITEM = "BOOK_INFO";

    private ImageView imgView;
    private TextView mTitle, mAuthor, mDescription;
    private DetailViewModel viewModel;
    private ExtendedFloatingActionButton fab;
    private RatingStarView ratingStarView;
    private Chip genre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        imgView = (ImageView) findViewById(R.id.detailCover);
        mTitle = (TextView) findViewById(R.id.detailTitle);
        mAuthor = (TextView) findViewById(R.id.detailAuthor);
        mDescription = (TextView) findViewById(R.id.bookDescription);
        fab = (ExtendedFloatingActionButton) findViewById(R.id.addfab);
        ratingStarView = (RatingStarView) findViewById(R.id.ratingStarView);
        genre = (Chip) findViewById(R.id.genreChip);

        setUpActionBar();

        Bundle extras = getIntent().getExtras();
        String item = extras.getString(BOOK_ITEM);
        String item2 = extras.getString("title");
        String item3 = extras.getString("author");

        Glide.with(this)
                .load(item)
                .transforms(new CenterCrop(), new RoundedCorners(50))
                .placeholder(new ColorDrawable(ContextCompat.getColor(this, R.color.dirty_white)))
                .into(imgView);

        mTitle.setText(item2);
        mAuthor.setText(item3);

        viewModel.getBookInfo("blood song").observe(this, bookitem -> {
            mDescription.setText(bookitem.getVolumeInfo().getCleanDescription());
            ratingStarView.setRating(3f);
            genre.setText(bookitem.getVolumeInfo().getCategories().get(0));
        });

        setFAB();

    }

    public void setFAB() {

        fab.setOnClickListener(view -> viewModel.saveCurrentBook());
    }

    public void setUpActionBar() {
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setElevation(0f);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
