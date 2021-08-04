package com.myapplicationdev.android.c347_l6_ps_know_your_facts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.crazyhitty.chdev.ks.rssmanager.RSS;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;

import java.util.List;

// TODO: RSS Feed | by Myron
public class Frag3 extends Fragment implements RssReader.RssCallback {

    TextView tvRss1, tvRss2;
    WebView wv1, wv2;
    View v;
    Button btnLoadRSS;
    RssReader rssReader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_frag3, container, false);

        rssReader = new RssReader(this);

        tvRss1 = v.findViewById(R.id.tvRss1);
        tvRss2 = v.findViewById(R.id.tvRss2);
        wv1 = v.findViewById(R.id.wv1);
        wv2 = v.findViewById(R.id.wv2);
        btnLoadRSS = v.findViewById(R.id.btnLoadRSS);

        btnLoadRSS.setOnClickListener(v -> {
            String[] url = new String[]{"https://www.singstat.gov.sg/rss"};
            rssReader.loadFeeds(url);
        });
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rssReader != null) {
            rssReader.destroy();
        }
    }

    @Override
    public void rssFeedsLoaded(List<RSS> rssList){
        RSS item = rssList.get(0);

        String title1 = item.getChannel().getItems().get(0).getTitle();
        String content1 = item.getChannel().getItems().get(0).getDescription();
        tvRss1.setText(title1);
        wv1.loadData(content1, "text/htm; charset=ut8", "utf-8");

        String title2 = item.getChannel().getItems().get(1).getTitle();
        String content2 = item.getChannel().getItems().get(1).getDescription();
        tvRss1.setText(title2);
        wv2.loadData(content2, "text/htm; charset=ut8", "utf-8");

    }

    @Override
    public void unableToReadRssFeeds(String errorMmessage){
        Toast.makeText(getActivity(), "RSS failed to load", Toast.LENGTH_SHORT).show();
    }



}

