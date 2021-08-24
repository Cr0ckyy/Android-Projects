package com.myapplicationdev.android.c347_l7_ex1_demo_cooldrawer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AnniversaryFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anniversary, container, false);
    }
}

