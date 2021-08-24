package com.myapplicationdev.android.c302_p07_ps_address_book;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ViewContactDetailsActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnUpdate, btnDelete;
    private int contactId;

    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_details);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        contactId = intent.getIntExtra("contact_id", -1);

        //TODO: call getContactDetails.php with the id as a parameter
        //TODO: set the text fields with the data retrieved
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams("id", contactId);

        client.get("http://10.0.2.2/C302_P07/getContactDetails.php", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Contact c = new Contact(response.getInt("id"), response.getString("firstname"), response.getString("lastname"), response.getString("mobile"));
                    etFirstName.setText(c.getFirstName());
                    etLastName.setText(c.getLastName());
                    etMobile.setText(c.getMobile());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                super.onSuccess(statusCode, headers, response);
            }
        });
        btnUpdate.setOnClickListener(this::btnUpdateOnClick);

        btnDelete.setOnClickListener(this::btnDeleteOnClick);
    }//end onCreate


    private void btnUpdateOnClick(View v) {

        //TODO: retrieve the updated text fields and set as parameters to be passed to updateContact.php
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id", contactId);
        params.put("firstname", etFirstName.getText().toString().trim());
        params.put("lastname", etLastName.getText().toString().trim());
        params.put("mobile", etMobile.getText().toString().trim());


        client.post("http://10.0.2.2/C302_P07/updateContact.php", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    message = response.getString("message");

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                super.onSuccess(statusCode, headers, response);
            }
        });


    }//end btnUpdateOnClick

    private void btnDeleteOnClick(View v) {
        //TODO: retrieve the id and set as parameters to be passed to deleteContact.php

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id", contactId);

        client.post("http://10.0.2.2/C302_P07/deleteContact.php", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    message = response.getString("message");

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                super.onSuccess(statusCode, headers, response);
            }
        });

    }//end btnDeleteOnClick

}//end class