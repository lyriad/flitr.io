package com.lyriad.flitrio.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lyriad.flitrio.Fragments.*;
import com.lyriad.flitrio.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navBar;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_bar_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_bar_profile:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.nav_bar_search:
                    selectedFragment = new SearchFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                    selectedFragment).addToBackStack(null).commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navBar = findViewById(R.id.main_nav_bar);
        navBar.setOnNavigationItemSelectedListener(navListener);
        navBar.setSelectedItemId(R.id.nav_bar_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                new HomeFragment()).addToBackStack(null).commit();
    }
}
