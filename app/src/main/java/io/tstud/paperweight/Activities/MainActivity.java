package io.tstud.paperweight.Activities;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import io.tstud.paperweight.Browse.BrowseFragment;
import io.tstud.paperweight.Fragments.ProfileFragment;
import io.tstud.paperweight.Home.HomeFragment;
import io.tstud.paperweight.Progress.ProgressFragment;
import io.tstud.paperweight.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationViewEx bottomNavigation;
    private TextView mTitle;
    private FragmentTransaction ft;
    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        setActionBar();
        setBottomNavigation();
        setDefaultScreen();

    }

    private void setActionBar() {
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setCustomView(R.layout.actionbar_slick);

        mTitle = (TextView) findViewById(R.id.dash_title);

        ab.setElevation(0f);
    }

    private void setBottomNavigation() {
        bottomNavigation = (BottomNavigationViewEx) findViewById(R.id.bnve);

        // Animations
        bottomNavigation.enableAnimation(false);
        bottomNavigation.enableShiftingMode(false);
        bottomNavigation.enableItemShiftingMode(false);

        //Text
        bottomNavigation.setTextVisibility(false);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ft = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()) {

                    case R.id.home_item:
                        mTitle.setText("Dashboard");
                        HomeFragment hf = HomeFragment.newInstance();
                        ft.replace(R.id.frame, hf);
                        ft.addToBackStack(null);
                        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ft.commit();
                        break;

                    case R.id.browse_item:
                        mTitle.setText("Browse");
                        BrowseFragment bf = BrowseFragment.newInstance();
                        ft.replace(R.id.frame, bf);
                        ft.addToBackStack(null);
                        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ft.commit();
                        break;

                    case R.id.progress_item:
                        mTitle.setText("Progress");
                        ProgressFragment pf = ProgressFragment.newInstance();
                        ft.replace(R.id.frame, pf);
                        ft.addToBackStack(null);
                        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ft.commit();
                        break;

                    case R.id.profile_item:
                        mTitle.setText("Profile");
                        ProfileFragment lf = ProfileFragment.newInstance();
                        ft.replace(R.id.frame, lf);
                        ft.addToBackStack(null);
                        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ft.commit();
                        break;

                }
                return true;
            }
        });
    }

    private void setDefaultScreen(){
        ft = getSupportFragmentManager().beginTransaction();
        HomeFragment hf = HomeFragment.newInstance();
        ft.replace(R.id.frame, hf);
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.commit();
    }
}
