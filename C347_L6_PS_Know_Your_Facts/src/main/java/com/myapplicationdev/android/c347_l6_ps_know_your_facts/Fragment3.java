package com.myapplicationdev.android.c347_l6_ps_know_your_facts;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.crazyhitty.chdev.ks.rssmanager.RSS;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;

import java.util.List;
import java.util.Random;

public class Fragment3 extends Fragment implements RssReader.RssCallback {
    static final String TAG = "Fragment3";
    Button btnChangeColor;
    TextView tvRSS;
    RssReader rssReader;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_3, container, false);

        btnChangeColor = fragmentView.findViewById(R.id.btnColorFrag3);
        tvRSS = fragmentView.findViewById(R.id.textViewRss);
        rssReader = new RssReader(this);

        String url = "https://www.singstat.gov.sg/rss";

        // TODO: rssReader loading web content url like online newspapers in one location for easy viewing.
        rssReader.loadFeeds(url);


        // TODO: color change for layout
        Random randomColorNo = new Random();
        btnChangeColor.setOnClickListener((View v) -> {
            float r = (float) (randomColorNo.nextFloat() / 2f + 0.5);
            float g = (float) (randomColorNo.nextFloat() / 2f + 0.5);
            float b = (float) (randomColorNo.nextFloat() / 2f + 0.5);
            int rgb = android.graphics.Color.rgb(r, g, b);
            fragmentView.setBackgroundColor(rgb);
        });

        return fragmentView;
    }


    @SuppressLint("SetTextI18n")
    @Override
    // TODO:  load the web content
    public void rssFeedsLoaded(List<RSS> rssList) {
        Random randomNumberGenerator = new Random();

        int number = randomNumberGenerator.nextInt(rssList.get(0).getChannel().getItems().size());
        String title = rssList.get(0).getChannel().getItems().get(number).getTitle();
        String desc = rssList.get(0).getChannel().getItems().get(number).getDescription();
        String link = rssList.get(0).getChannel().getItems().get(number).getLink();

        tvRSS.setText("Title: " + title + "\n\n" + "Description: " + desc + "\n\n" + "Link: " + link + "\n\n");
    }

    @Override
    public void unableToReadRssFeeds(String errorMessage) {
        Log.e(TAG, errorMessage);

    }
}