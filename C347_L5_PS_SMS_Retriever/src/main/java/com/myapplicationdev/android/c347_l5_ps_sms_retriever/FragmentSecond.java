package com.myapplicationdev.android.c347_l5_ps_sms_retriever;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSecond extends Fragment {
    Button btnRetrieveWord;
    EditText etWord;
    TextView tvWordResult;

    public FragmentSecond() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_second, container, false);
        etWord = view.findViewById(R.id.etWord);
        btnRetrieveWord = view.findViewById(R.id.btnRetrieveWord);
        tvWordResult = view.findViewById(R.id.tvWordResult);

        btnRetrieveWord.setOnClickListener(view1 -> {
            int permissionCheck = PermissionChecker.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS);

            if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS}, 0);
                return;
            }
            Uri uri = Uri.parse("content://sms");

            String[] reqCols = new String[]{"date", "address", "body", "type"};

            ContentResolver cr = getActivity().getContentResolver();


            // TODO: 4.	When user enters a word under Only SMS with this word, 
            //  it will retrieve only SMS that contains the entered keyword
            StringBuilder filter = new StringBuilder("body LIKE ?");
            String word = etWord.getText().toString();
            String[] splitedWord = word.split(" ");
            String[] filterArgs = new String[splitedWord.length];

            for (int i = 0; i < splitedWord.length; i++) {
                if (splitedWord.length != 1) {
                    filter.append(" OR body LIKE ?");
                }
                filterArgs[i] = "%" + splitedWord[i] + "%";
            }

            @SuppressLint("Recycle") Cursor cursor = cr.query(uri, reqCols, filter.toString(), filterArgs, null);
            String smsBody = "";
            if (cursor.moveToFirst()) {
                do {
                    long dateInMillies = cursor.getLong(0);
                    String date = (String) DateFormat.format("dd MM yyyy h:mm:ss aa", dateInMillies);
                    String address = cursor.getString(1);
                    String body = cursor.getString(2);
                    String type = cursor.getString(3);
                    if (type.equalsIgnoreCase("1")) {
                        type = "Inbox:";
                    } else {
                        type = "Sent:";
                    }
                    smsBody += type + " " + address + "\n at" + date + "\n\"" + body + "\"\n\n";
                } while (cursor.moveToNext());
            }
            tvWordResult.setText(smsBody);
        });
        return view;
    }
}