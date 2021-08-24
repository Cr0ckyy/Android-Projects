package com.myapplicationdev.android.c347_l7_ex1_demo_cooldrawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    String[] drawerItems;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayAdapter<String> arrayAdapter;
    String currentTitle;
    ActionBar actionBar;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: the "Hamburger" Menu
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.left_drawer);

        // TODO: the "Hamburger" Menu Items
        drawerItems = new String[]{"Biography", "Vaccination", "Anniversary"};
        actionBar = getSupportActionBar();

        // the currentTitle will be the selected drawer Item
        currentTitle = this.getTitle().toString();

        // on and off switch for the ActionBar
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,    /* DrawerLayout object */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close)/* "close drawer" description */ {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                actionBar.setTitle(currentTitle);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                actionBar.setTitle("Make a selection");
            }
        };

        // adding the actionBarDrawerToggle to drawerLayout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // after done selecting the drawer Item , it would return to homepage
        actionBar.setDisplayHomeAsUpEnabled(true);

        // set Adapter for drawerList
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, drawerItems);
        drawerList.setAdapter(arrayAdapter);

        // TODO: clicking action of drawerList
        drawerList.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            fragment = null;
            // positions of the fragments
            if (position == 0) {
                fragment = new BiographyFragment();
            } else if (position == 1) {
                fragment = new VaccinationFragment();
            } else if (position == 2) {
                fragment = new AnniversaryFragment();
            }

            // setting of fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();

            // when clicking a drawer item
            drawerList.setItemChecked(position, true);
            currentTitle = drawerItems[position];

            //  Set the drawer toggle as the DrawerListener
            actionBar.setTitle(currentTitle);
            drawerLayout.closeDrawer(drawerList);
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
        // Called by the system when the device configuration changes while your activity is running
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

