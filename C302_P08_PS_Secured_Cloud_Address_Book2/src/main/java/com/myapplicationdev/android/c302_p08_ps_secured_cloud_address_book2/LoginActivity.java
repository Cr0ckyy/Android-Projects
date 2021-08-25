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

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener((View v) -> {

            // TODO (1) When Login button is clicked, call doLogin.php web service to check if the user is able to log in
            // What is the web service URL?
            // What is the HTTP method?
            // What parameters need to be provided?
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.length() != 0 && password.length() != 0) {

                // TODO: create HTTP client that send data (POST parameters) to MYSQL
                AsyncHttpClient client = new AsyncHttpClient();
                String url = "http://10.0.2.2/C302_P08_SecuredCloudAddressBook/doLogin.php";
                RequestParams params = new RequestParams();
                params.put("username", username);
                params.put("password", password);
                client.post(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        authenticateUser(response);
                    }
                });

            } else {
                Toast.makeText(LoginActivity.this,
                        "Check that all of the EditTexts are filled out.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO (2) Using AsyncHttpClient, check if the user has been authenticated successfully
    // If the user can log in, extract the id and API Key from the JSON object, set them into Intent and start MainActivity Intent.
    // If the user cannot log in, display a toast to inform user that login has failed.
    void authenticateUser(JSONObject user) {
        try {
            String id = user.getString("id");
            String apiKey = user.getString("apikey");

            // TODO: MainActivity receives the user's id and apikey after loggin in
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            mainIntent.putExtra("loginId", id);
            mainIntent.putExtra("apikey", apiKey);

            startActivity(mainIntent);
            Toast.makeText(LoginActivity.this, "Login success.", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}