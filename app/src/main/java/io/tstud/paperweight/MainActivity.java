package io.tstud.paperweight;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import io.tstud.paperweight.Fragments.BrowseFragment;
import io.tstud.paperweight.Fragments.HomeFragment;
import io.tstud.paperweight.Fragments.ProfileFragment;
import io.tstud.paperweight.Fragments.ProgressFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationViewEx bottomNavigation;
    private TextView mTitle;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        HomeFragment hf = new HomeFragment();
                        ft.replace(R.id.frame, hf);
                        ft.addToBackStack(null);
                        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ft.commit();
                        break;

                    case R.id.browse_item:
                        mTitle.setText("Browse");
                        BrowseFragment bf = new BrowseFragment();
                        ft.replace(R.id.frame, bf);
                        ft.addToBackStack(null);
                        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ft.commit();
                        break;

                    case R.id.progress_item:
                        mTitle.setText("Progress");
                        ProgressFragment pf = new ProgressFragment();
                        ft.replace(R.id.frame, pf);
                        ft.addToBackStack(null);
                        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ft.commit();
                        break;

                    case R.id.profile_item:
                        mTitle.setText("Profile");
                        ProfileFragment lf = new ProfileFragment();
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
        HomeFragment hf = new HomeFragment();
        ft.replace(R.id.frame, hf);
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.commit();
    }
}
