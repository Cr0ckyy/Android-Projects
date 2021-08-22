package com.myapplicationdev.android.c347_l6_ps_know_your_facts;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<Fragment> fragments;
    MyFragmentPagerAdapter adapter;
    ViewPager vPager;
    Button btnCloseActivity;
    SharedPreferences savedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vPager = findViewById(R.id.viewpager);
        btnCloseActivity = findViewById(R.id.buttonLater);

        // TODO: Return the FragmentManager for interacting with this activity's fragments.
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragments = new ArrayList<>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());

        // add fragments view into the MyFragmentPagerAdapter
        adapter = new MyFragmentPagerAdapter(fragmentManager, fragments);

        vPager.setAdapter(adapter);

        btnCloseActivity.setOnClickListener((View v) -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Random randomPageNo = new Random();
        int itemId = item.getItemId();

        if (itemId == R.id.action_next) {
            int max = vPager.getChildCount();
            if (vPager.getCurrentItem() < max - 1) {
                vPager.setCurrentItem(vPager.getCurrentItem() + 1, true);
            }

        } else if (itemId == R.id.action_previous) {
            if (vPager.getCurrentItem() > 0) {
                int previousPage = vPager.getCurrentItem() - 1;
                vPager.setCurrentItem(previousPage, true);
            }

        } else if (itemId == R.id.action_random) {
            int randomPage = randomPageNo.nextInt(vPager.getChildCount());
            System.out.println(randomPage);
            vPager.setCurrentItem(randomPage, true);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    // TODO: Called when the user no longer interacts with the activity but it is still visible on screen.
    protected void onPause() {
        SharedPreferences.Editor editor = savedIndex.edit();
        editor.putInt("savedIndex", vPager.getCurrentItem());
        editor.apply();
        super.onPause();
    }

    @Override
    // TODO: Call this after onPause/nRestart/onRestoreInstanceState to interact with the user
    protected void onResume() {
        savedIndex = getSharedPreferences("savedIndex", MODE_PRIVATE);
        vPager.setCurrentItem(savedIndex.getInt("savedIndex", 0));
        super.onResume();
    }
}