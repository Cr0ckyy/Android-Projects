package com.myapplicationdev.android.c302_p13_ps_movie_db;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {


    EditText etLoginID, etPassword;
    Button btnLogin;
    AsyncHttpClient client;
    SharedPreferences sharedLogin;
    RequestParams params;
    String url;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etLoginID = findViewById(R.id.editTextLoginID);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);

        sharedLogin = getSharedPreferences("C302_P13", Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> login());


    }

    // TODO:  login Method
    private void login() {

        // TODO:   When the Login button is clicked, call the doLogin.php web service to see if the user can log in.
        //What is the URL of the web service?
        //What exactly is the HTTP method?
        //What parameters must be supplied?
        username = etLoginID.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        // Ensure that the user login input is not empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Both username and password cannot be left blank.",
                    Toast.LENGTH_SHORT).show();
        } else {

            // TODO: Login , by creating HTTP client that send data (POST parameters) to MYSQL
            client = new AsyncHttpClient();
            params = new RequestParams();
            params.add("username", username);
            params.add("password", password);
            url = "http://10.0.2.2/C302_P13_OMDB/doLogin.php";

            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    try {

                        // TODO: once user's authenticated , user credentials will be passed to MainActivity
                        if (response.getBoolean("authenticated")) {

                            // enable sharedLogin to write data
                            SharedPreferences.Editor editor = sharedLogin.edit();

                            // adding data to sharedLogin
                            editor.putInt("user_id", response.getInt("id"));
                            editor.putString("apikey", response.getString("apikey"));
                            editor.apply(); // enable adding of data to sharedLogin

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Toast.makeText(LoginActivity.this, "Successful login.", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}


