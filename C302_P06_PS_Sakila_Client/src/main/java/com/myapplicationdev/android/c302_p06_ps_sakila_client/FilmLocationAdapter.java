package com.myapplicationdev.android.c302_p06_ps_sakila_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmLocationAdapter extends ArrayAdapter<FilmLocation> {
    ArrayList<FilmLocation> myFilmLocationArrayList;
    Context context;
    TextView myTextViewAddress, myTextViewPostalCode, myTextViewPhone, myTextViewCity, myTextViewCountry;

    public FilmLocationAdapter(Context context, int resource, ArrayList<FilmLocation> objects) {
        super(context, resource, objects);
        myFilmLocationArrayList = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.activity_search_list, parent, false);

        myTextViewAddress = rowView.findViewById(R.id.tvSearchAddress);
        myTextViewPostalCode = rowView.findViewById(R.id.tvSearchPostalCode);
        myTextViewPhone = rowView.findViewById(R.id.tvSearchPhone);
        myTextViewCity = rowView.findViewById(R.id.tvSearchCity);
        myTextViewCountry = rowView.findViewById(R.id.tvSearchCountry);


        FilmLocation myCurrentFilmLocation = myFilmLocationArrayList.get(position);
        myTextViewAddress.setText(myCurrentFilmLocation.getAddress());
        myTextViewPostalCode.setText(String.valueOf(myCurrentFilmLocation.getPostal_code()));
        myTextViewPhone.setText(String.valueOf(myCurrentFilmLocation.getPhone()));
        myTextViewCity.setText(myCurrentFilmLocation.getCity());
        myTextViewCountry.setText(myCurrentFilmLocation.getCountry());

        return rowView;
    }
}
