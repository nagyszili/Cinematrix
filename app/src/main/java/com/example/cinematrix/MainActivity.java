package com.example.cinematrix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavBar = findViewById(R.id.nav_view);
        bottomNavBar.setVisibility(View.INVISIBLE);


        bottomNavBar.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment selectedFragment = new HomeFragment(this);

            switch (menuItem.getItemId()) {

                case R.id.navigation_home: {
                    selectedFragment = new HomeFragment(this);
                    bottomNavBar.getMenu().getItem(0).setChecked(true);
                    break;
                }
                case R.id.navigation_favorites: {
                    selectedFragment = new FavoritesFragment(this);
                    bottomNavBar.getMenu().getItem(1).setChecked(true);
                    break;
                }
                case R.id.navigation_now_in_cinema: {
                    selectedFragment = new NowInCinemaFragment();
                    bottomNavBar.getMenu().getItem(2).setChecked(true);

                    break;
                }
                case R.id.navigation_profile: {
                    selectedFragment = new ProfileFragment(this);
                    bottomNavBar.getMenu().getItem(3).setChecked(true);
                    break;
                }

            }

            FragmentManager fragmentManagerNav = getSupportFragmentManager();
            FragmentTransaction transactionNav = fragmentManagerNav.beginTransaction();
            transactionNav.replace(R.id.container, selectedFragment);
            transactionNav.commit();

            return false;
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment(this, this.bottomNavBar);
//        loginFragment.setArguments(getIntent().getExtras());
        transaction.add(R.id.container, loginFragment);
//        transaction.addToBackStack(null);
        transaction.commit();



    }

}
