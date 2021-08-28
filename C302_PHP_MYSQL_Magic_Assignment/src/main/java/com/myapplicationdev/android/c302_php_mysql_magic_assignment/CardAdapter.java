package com.myapplicationdev.android.c302_php_mysql_magic_assignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CardAdapter extends ArrayAdapter<Card> {

    public static final String LOG_TAG = CardAdapter.class.getName();

    ArrayList<Card> alCard;
    Context context;

    public CardAdapter(Context context, int resource, ArrayList<Card> objects) {
        super(context, resource, objects);
        alCard = objects;
        this.context = context;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.card_row, parent, false);

        TextView tvName = rowView.findViewById(R.id.tvName);
        TextView tvPrice = rowView.findViewById(R.id.tvPrice);

        Card card = alCard.get(position);

        tvName.setText(card.getCardName());
        tvPrice.setText(String.format("%.2f", card.getPrice()));


        return rowView;
    }

}