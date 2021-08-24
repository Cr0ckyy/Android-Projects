package com.myapplicationdev.android.c347_l7_ps_my_data_book;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class BioFragment extends Fragment {
    static final int MODE_PRIVATE = 0;
    ActionBar ab;
    Button btnFragBio;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TextView tvShow;
    SharedPreferences savedText;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bio, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert ab != null;
        // TODO: enable "Return arrow" function in ActionBar
        ab.setDisplayHomeAsUpEnabled(true);
        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        tvShow = view.findViewById(R.id.tvShow);
        btnFragBio = view.findViewById(R.id.btnFragBio);

        btnFragBio.setOnClickListener((View v) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater1 = requireActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater1.inflate(R.layout.customer_alert1, null))
                    // Add action buttons

                    .setPositiveButton("Ok", (DialogInterface dialog, int id) -> {
                        // sign in the user ...
                        Dialog newDialog = (Dialog) dialog;
                        EditText newBio = newDialog.findViewById(R.id.etNewAnni);
                        if (!newBio.getText().toString().isEmpty()) {
                            tvShow.setText(newBio.getText().toString());
                        } else {
                            Toast.makeText(getContext(), "Enter Something!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", (DialogInterface dialog, int id) -> dialog.dismiss());
            builder.create().show();
        });

        fab.setOnClickListener((View v) -> drawerLayout.openDrawer(Gravity.START));
        return view;

    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = savedText.edit();
        editor.putString("savedText3", tvShow.getText().toString());
        editor.apply();
        super.onPause();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onResume() {
        savedText = requireActivity().getSharedPreferences("savedText3", MODE_PRIVATE);
        tvShow.setText(savedText.getString("savedText3", ""));
        super.onResume();
    }


}