package com.myapplicationdev.android.c302_php_mysql_magic_assignment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getName();
    // TODO: declare sharedPreferences that can be used to save user data around the application even if it destroyed
    static final String SHARED_PREFERENCES = "sharedPreferences";
    static final String SHARED_LOGIN_ID = "sharedLoginID";
    static final String SHARED_PASSWORD = "sharedPassword";
    EditText etLoginId, etPassword;
    Button btnLogin;
    // TODO: declare variables to enable users to log in
    String username, password;
    String savedLoginId;
    String savedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ensure that internet-related services are fully granted throughout the application
        @SuppressLint("InlinedApi") String[] permissions = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };
        ActivityCompat.requestPermissions(this, permissions, 0);
        getLocalIpAddress();

        etLoginId = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // TODO: get SharedPreferences so that the user does not have to login again after "destroying" the app.
        getSharedPreferences();

        btnLogin.setOnClickListener((View v) -> login());

    }

    // TODO:  login Method
    private void login() {

        // TODO:   When the Login button is clicked, call the doLogin.php web service to see if the user can log in.
        //What is the URL of the web service?
        //What exactly is the HTTP method?
        //What parameters must be supplied?
        username = etLoginId.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        // Ensure that the user login input is not empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Both username and password cannot be left blank.",
                    Toast.LENGTH_SHORT).show();
        } else {

            // TODO: Login , by creating HTTP client that send data (POST parameters) to MYSQL
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            String url = "http://10.0.2.2/19039480_c302_magic/doLogin.php";
            params.put("username", username);
            params.put("password", password);
            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    authenticateUser(response);
                }
            });
        }

    }


    /* TODO (2) Using AsyncHttpClient, determine whether the user has been successfully authenticated.
     If the user is able to log in, obtain the id and API Key from the JSON object.
     Convert them to Intent and begin MainActivity Intent. If the user is unable to log in,
     a toast should be displayed to inform the user that the login has failed. */
    private void authenticateUser(JSONObject user) {
        try {
            // TODO: get user login inputs
            String username = etLoginId.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // TODO: add User Login Data into SharedPreferences
            addSharedPreferences(username, password);

            // TODO: get user credentials
            String id = user.getString("id");
            String apiKey = user.getString("apikey");
            String role = user.getString("role");

            // TODO:prepare user data for passing to MainActivity
            Intent myMainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
            myMainActivityIntent.putExtra("loginId", id);
            myMainActivityIntent.putExtra("apikey", apiKey);
            myMainActivityIntent.putExtra("role", role);
            startActivity(myMainActivityIntent);
            Toast.makeText(LoginActivity.this, "Login was successful.", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            Toast.makeText(LoginActivity.this, "The login process was unsuccessful. " +
                            "Please double-check your login information.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: add SharedPreferences to be used throughout the application
    public void addSharedPreferences(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_LOGIN_ID, username);
        editor.putString(SHARED_PASSWORD, password);
        editor.apply();
    }

    // TODO: get SharedPreferences to be used throughout the application
    public void getSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        savedLoginId = sharedPreferences.getString(SHARED_LOGIN_ID, "");
        savedPassword = sharedPreferences.getString(SHARED_PASSWORD, "");
        etLoginId.setText(savedLoginId);
        etPassword.setText(savedPassword);

        if (savedLoginId.isEmpty() || savedPassword.isEmpty()) {
            return;
        }
        login();

    }

    public void getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses(); addressEnumeration.hasMoreElements(); ) {
                    InetAddress inetAddress = addressEnumeration.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String localIpAddressMsg = String.format("%s: Android Emulator's IP address: %s", TAG, inetAddress.getHostAddress());
                        System.out.println(localIpAddressMsg);
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
    }
}