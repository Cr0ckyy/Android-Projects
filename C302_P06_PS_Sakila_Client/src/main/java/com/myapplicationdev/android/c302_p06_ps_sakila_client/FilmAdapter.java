package com.myapplicationdev.android.c302_p06_ps_sakila_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmAdapter extends ArrayAdapter<Film> {


    ArrayList<Film> myFilmArrayList;
    Context context;
    TextView myTextViewTitle, myTextViewYear, myTextViewRating;

    public FilmAdapter(Context context, int resource, ArrayList<Film> objects) {
        super(context, resource, objects);
        myFilmArrayList = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.activity_film_list, parent, false);

        myTextViewTitle = rowView.findViewById(R.id.tvTitle);
        myTextViewYear = rowView.findViewById(R.id.tvYear);
        myTextViewRating = rowView.findViewById(R.id.tvRating);

        Film myCurrentFilm = myFilmArrayList.get(position);
        myTextViewTitle.setText(myCurrentFilm.getTitle());
        myTextViewYear.setText(String.valueOf(myCurrentFilm.getRelease_year()));
        myTextViewRating.setText(myCurrentFilm.getRating());

        return rowView;
    }
}
