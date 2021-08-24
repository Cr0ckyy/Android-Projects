package com.myapplicationdev.android.c347_l7_ps_my_data_book;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBar actionBar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Fragment fragment;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationDrawer();
        this.getSharedPreferences("hi", 0);

    }

    public void setNavigationDrawer() {

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        actionBar = getSupportActionBar();
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Set the list's click listener
        navigationView.setNavigationItemSelectedListener((MenuItem item) -> {

            fragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.bio) {
                fragment = new BioFragment();
            } else if (itemId == R.id.vaccination) {
                fragment = new VaccinationFragment();
            } else if (itemId == R.id.anniversary) {
                fragment = new AnniversaryFragment();
            } else if (itemId == R.id.about) {
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
            if (fragment != null) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "The fragment is empty.", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync toggle state so the indicator is shown properly.
        //  Have to call in onPostCreate()
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

}