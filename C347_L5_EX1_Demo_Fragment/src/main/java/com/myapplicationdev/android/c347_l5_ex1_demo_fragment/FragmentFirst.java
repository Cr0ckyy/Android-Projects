package com.myapplicationdev.android.c347_l5_ex1_demo_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


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
public class FragmentFirst extends Fragment {
    Button btnAddText;
    TextView tvFrag1;

    public FragmentFirst() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  TODO: Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_first, container, false);

        //  TODO: Get the View object to use its  findViewById() method
        tvFrag1 = fragmentView.findViewById(R.id.tvFrag1);
        btnAddText = fragmentView.findViewById(R.id.btnAddTextFrag);

        btnAddText.setOnClickListener((View view1) -> {

            String data = tvFrag1.getText().toString() + "\n" + "New Data";
            tvFrag1.setText(data);

        });
        return fragmentView;
    }
}