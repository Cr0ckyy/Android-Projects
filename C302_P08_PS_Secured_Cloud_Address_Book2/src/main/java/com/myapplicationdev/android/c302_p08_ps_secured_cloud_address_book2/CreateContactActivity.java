package com.myapplicationdev.android.c302_p08_ps_secured_cloud_address_book2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CreateContactActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etMobile;
    Button btnCreate;
    String loginId, apikey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnCreate = findViewById(R.id.btnCreate);

        Intent loginIntent = getIntent();
        loginId = loginIntent.getStringExtra("loginId");
        apikey = loginIntent.getStringExtra("apikey");

        btnCreate.setOnClickListener((View v) -> {

            String fName = etFirstName.getText().toString().trim();
            String lName = etLastName.getText().toString().trim();
            String mobile = etMobile.getText().toString().trim();

            if (fName.length() != 0 && lName.length() != 0 && mobile.length() != 0) {

                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("loginId", loginId);
                params.put("apikey", apikey);
                params.put("FirstName", fName);
                params.put("LastName", lName);
                params.put("Mobile", mobile);
                String url = "http://10.0.2.2/C302_P08_SecuredCloudAddressBook/createContact.php";

                client.post(url, params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (JSONException e) {
                            Toast.makeText(CreateContactActivity.this,
                                    "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            } else {
                Toast.makeText(CreateContactActivity.this,
                        "Check that all of the EditTexts are filled out.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}