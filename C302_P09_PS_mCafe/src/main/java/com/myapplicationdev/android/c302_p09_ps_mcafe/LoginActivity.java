
package com.myapplicationdev.android.c302_p09_ps_mcafe;

import android.content.Intent;
import android.content.SharedPreferences;
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

    static final String TAG = "LoginActivity";
    EditText etLoginID, etPassword;
    Button btnSubmit;
    String username;
    String password;

    // Set up sharedPreferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LOGIN_ID = "sharedLoginID";
    public static final String PASSWORD = "sharedPassword";

    String savedLoginId;
    String savedPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginID = findViewById(R.id.editTextLoginID);
        etPassword = findViewById(R.id.editTextPassword);
        btnSubmit = findViewById(R.id.buttonSubmit);

        loadSharedPref();

        btnSubmit.setOnClickListener((View v) -> {
            username = etLoginID.getText().toString().trim();
            password = etPassword.getText().toString().trim();

            if (username.equalsIgnoreCase("")) {
                Toast.makeText(LoginActivity.this, "Login failed. Please enter username.", Toast.LENGTH_LONG).show();

            } else if (password.equalsIgnoreCase("")) {
                Toast.makeText(LoginActivity.this, "Login failed. Please enter password.", Toast.LENGTH_LONG).show();

            } else {
                //proceed to authenticate user
                OnLogin();
            }
        });
    }

    void OnLogin() {

        // TODO: Login , by creating HTTP client that send data (POST parameters) to MYSQL
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://10.0.2.2/C302_P09_mCafe/doLogin.php";
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // TODO: Upon successful user input, MYSQL's JSONObject responses are returned.
                    String id = response.getString("id");
                    String apiKey = response.getString("apikey");

                    // TODO: MainActivity will receive the responses.
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.putExtra("loginId", id);
                    mainIntent.putExtra("apikey", apiKey);

                    saveSharedPref(username, password);

                    startActivity(mainIntent);
                    Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                }
            }

        });

    }

    /* ------------------------------------ METHODS LANGUAGE SHARED PREF - START ------------------------------------ */

    // TODO: save password and id as SharedPreferences for app usage
    private void saveSharedPref(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN_ID, username);
        editor.putString(PASSWORD, password);

        editor.apply();

        //Toast.makeText(this, "Data saved.", Toast.LENGTH_SHORT).show();
    }

    // TODO: load password and id as SharedPreferences for app usage
    private void loadSharedPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        savedLoginId = sharedPreferences.getString(LOGIN_ID, "");
        savedPassword = sharedPreferences.getString(PASSWORD, "");

        etLoginID.setText(savedLoginId);
        etPassword.setText(savedPassword);

        if (!savedLoginId.equals("") && !savedPassword.equals("")) {

            // TODO: Login, by creating HTTP client that send data (POST parameters) to MYSQL
            AsyncHttpClient client = new AsyncHttpClient();
            String url = "http://10.0.2.2/C302_P09_mCafe/doLogin.php";
            RequestParams params = new RequestParams();
            params.put("username", savedLoginId);
            params.put("password", savedPassword);

            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String id = response.getString("id");
                        String apiKey = response.getString("apikey");

                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        mainIntent.putExtra("loginId", id);
                        mainIntent.putExtra("apikey", apiKey);

                        startActivity(mainIntent);

                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

            });
        }
    }


    /* ------------------------------------ METHODS LANGUAGE SHARED PREF - END ------------------------------------ */


}


