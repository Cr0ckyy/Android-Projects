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

public class CreateContactActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCreateOnClick(v);
            }
        });

    }//end onCreate


    private void btnCreateOnClick(View v) {
        //TODO: call createContact.php to save new contact details
        String fName = etFirstName.getText().toString().trim();
        String lName = etLastName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();

        if (fName.length() != 0 && lName.length() != 0 && mobile.length() != 0) {
            int mobileNum = Integer.parseInt(etMobile.getText().toString().trim());

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("firstname", fName);
            params.put("lastname", lName);
            params.put("mobile", mobileNum);

            client.post("http://10.0.2.2/C302_P07/addContact.php", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        String message = response.getString("message");

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        finish();

                    } catch (JSONException e) {

                    }
                    Toast.makeText(getApplicationContext(), "Contact details successfully created", Toast.LENGTH_SHORT).show();

//                super.onSuccess(statusCode, headers, response);
                }
            });
        }


    }//end btnCreateOnClick
}