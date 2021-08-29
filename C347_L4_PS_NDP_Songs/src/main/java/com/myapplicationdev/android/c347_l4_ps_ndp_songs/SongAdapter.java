package com.myapplicationdev.android.c347_l4_ps_ndp_songs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {
    Context context;
    ArrayList<Song> songs;
    int resource;
    ImageView iv1, iv2, iv3, iv4, iv5;
    TextView tvSinger, tvYear, tvTitle;

    public SongAdapter(Context context, int resource, ArrayList<Song> songs) {
        super(context, resource, songs);
        this.context = context;
        this.songs = songs;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.row, parent, false);

        //Match the UI components with Java variables
        tvSinger = rowView.findViewById(R.id.textViewSinger);
        tvYear = rowView.findViewById(R.id.textViewYear);
        tvTitle = rowView.findViewById(R.id.textViewTitle);

        iv1 = rowView.findViewById(R.id.imageView1star);
        iv2 = rowView.findViewById(R.id.imageView2star);
        iv3 = rowView.findViewById(R.id.imageView3star);
        iv4 = rowView.findViewById(R.id.imageView4star);
        iv5 = rowView.findViewById(R.id.imageView5star);

        Song note = songs.get(position);
        int stars = note.getStars();

        String singer = note.getSingers();
        int year = note.getYear();
        String title = note.getTitle();
        tvSinger.setText(singer);
        tvYear.setText("" + year);
        tvTitle.setText(title);

        //Check if the property for starts >= 5, if so, "light" up the stars
        if (stars == 5) {
            iv5.setImageResource(android.R.drawable.btn_star_big_on);
            iv4.setImageResource(android.R.drawable.btn_star_big_on);
            iv3.setImageResource(android.R.drawable.btn_star_big_on);
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        } else if (stars == 1) {
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        } else if (stars == 2) {
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        } else if (stars == 3) {
            iv3.setImageResource(android.R.drawable.btn_star_big_on);
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        } else if (stars == 4) {
            iv4.setImageResource(android.R.drawable.btn_star_big_on);
            iv3.setImageResource(android.R.drawable.btn_star_big_on);
            iv2.setImageResource(android.R.drawable.btn_star_big_on);
            iv1.setImageResource(android.R.drawable.btn_star_big_on);
        }

        return rowView;
    }


}


