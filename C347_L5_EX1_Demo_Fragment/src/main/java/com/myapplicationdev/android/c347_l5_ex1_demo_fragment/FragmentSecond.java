package com.myapplicationdev.android.c347_l5_ex1_demo_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * TODO: The fragment must extend Fragment
 * Return the View object built from the layout file in onCreateView().
 * You can  access the View object if needed.
 * The layout file is just like the layout file for
 * activity where we can add TextView etc
 */
/*
The majority of the time, we can code as if it were an Activity.
The event listeners (such as OnClickListeners) for Fragment's buttons will be defined in the Fragment.
* */
public class FragmentSecond extends Fragment {
    Button btnAddText;
    TextView tvFrag2;

    public FragmentSecond() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        tvFrag2 = view.findViewById(R.id.tvFrag2);
        btnAddText = view.findViewById(R.id.btnAddTextFrag2);

        btnAddText.setOnClickListener(view1 -> {
            String data = tvFrag2.getText().toString() + "\n" + "New Data F2";
            tvFrag2.setText(data);
        });
        // Inflate the layout for this fragment
        return view;
    }
}