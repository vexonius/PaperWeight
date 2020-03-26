package io.tstud.paperweight.BookDetail;

//komentar dodan

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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
    private String id;


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
        id = extras.getString("book_id");
        String item2 = extras.getString("title");
        String item3 = extras.getString("author");

        Glide.with(this)
                .asBitmap()
                .load(item)
                .transform( new RoundedCorners(50))
                .placeholder(new ColorDrawable(ContextCompat.getColor(this, R.color.dirty_white)))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imgView.setImageBitmap(resource);
                        createPaletteAsync(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        mTitle.setText(item2);
        mAuthor.setText(item3);

        viewModel.setBookId(id);

        viewModel.getBookInfo().observe(this, bookitem -> {
            float rating = bookitem.getVolumeInfo().getAverageRating().floatValue();
            mDescription.setText(bookitem.getVolumeInfo().getCleanDescription());
            ratingStarView.setRating((int) rating);
            genre.setText(bookitem.getVolumeInfo().getCategories().get(0));
        });



        setFAB();
        extendDescriptionListener();

    }

    public void setFAB() {

        fab.setOnClickListener(view -> viewModel.setCurrentlyReadingBook(id));
    }


    public void extendDescriptionListener(){
        mDescription.setOnClickListener(view -> mDescription.setMaxLines(40));
    }

    public void setUpActionBar() {
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setElevation(0f);
    }

    public void createPaletteAsync(Bitmap bitmap) {
        Palette.from(bitmap).generate(p -> {
            int vibrantColor = p.getDarkMutedColor(ContextCompat.getColor(getApplicationContext(), R.color.dirty_white));
            int mutedLightColor = p.getLightMutedColor(ContextCompat.getColor(getApplicationContext(), R.color.darken));
            fab.setBackgroundColor(vibrantColor);
            fab.setTextColor(mutedLightColor);
            genre.setChipStrokeColor(ColorStateList.valueOf(mutedLightColor));
        });
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
