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


public class AnniversaryFragment extends Fragment {

    static final int MODE_PRIVATE = 0;
    ActionBar actionBar;
    Button btnAnniversaryFragment;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TextView tvShow;
    SharedPreferences savedText;

    @SuppressLint({"WrongConstant", "InflateParams"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anniversary, container, false);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab3);
        tvShow = view.findViewById(R.id.tvShowAnniv);
        btnAnniversaryFragment = view.findViewById(R.id.btnFragAnni);
        drawerLayout = view.findViewById(R.id.drawer_layout);

        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        // TODO: enable "Return arrow" function in ActionBar
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);


        floatingActionButton.setOnClickListener((View v) -> drawerLayout.openDrawer(Gravity.START));

        btnAnniversaryFragment.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            // Get the layout inflater
            LayoutInflater inflater1 = requireActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater1.inflate(R.layout.customer_alert3, null))
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

        return view;
    }

    @Override
    // TODO: onPause is called when the Fragment is no longer resumed. 
    public void onPause() {
        SharedPreferences.Editor editor = savedText.edit();
        editor.putString("savedText", tvShow.getText().toString());
        editor.apply();
        super.onPause();
    }

    @SuppressLint("WrongConstant")
    @Override
    // TODO: onResume is called when the fragment is visible to the user and actively running.
    public void onResume() {
        savedText = requireActivity().getSharedPreferences("savedText", MODE_PRIVATE);
        tvShow.setText(savedText.getString("savedText", ""));
        super.onResume();
    }

}