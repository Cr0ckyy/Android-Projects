package com.myapplicationdev.android.c302_php_mysql_magic_assignment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

public class CreateCardActivity_19039480 extends AppCompatActivity {
    public static final String TAG = CreateCardActivity_19039480.class.getName();
    // TODO: declare sharedPreferences that can be used to pass user data around the application
    static final String SHARED_PREFERENCES = "sharedPreferences";
    EditText etName, etColour, etType, etPrice, etQuantity;
    Button btnAdd;
    // TODO (3) Declare loginId and apikey
    String loginId, apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        // Ensure that internet-related services are fully granted throughout the application
        @SuppressLint("InlinedApi") String[] permissions = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };
        ActivityCompat.requestPermissions(this, permissions, 0);
        getLocalIpAddress();

        etName = findViewById(R.id.etName);
        etColour = findViewById(R.id.etColour);
        etType = findViewById(R.id.etType);
        etPrice = findViewById(R.id.etPrice);
        etQuantity = findViewById(R.id.etQuantity);
        btnAdd = findViewById(R.id.btnAdd);


        // TODO: Get loginId, apikey and role from the LoginActivity
        Intent loginIntent = getIntent();
        loginId = loginIntent.getStringExtra("loginId");
        apikey = loginIntent.getStringExtra("apikey");

        btnAdd.setOnClickListener((View v) -> addCard());
    }


    // TODO: addCard method
    private void addCard() {

        boolean proceed = true;

        // obtain the user's input of card data from the user
        String name = etName.getText().toString().trim();
        String colourID = etColour.getText().toString().trim();
        String typeID = etType.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();

        // examining user input for emptiness and implementing required conditions
        if (name.isEmpty()) {
            Toast.makeText(CreateCardActivity_19039480.this, "The card's name cannot be left blank.",
                    Toast.LENGTH_SHORT).show();
            proceed = false;

        } else if (colourID.isEmpty() || (Integer.parseInt(colourID) < 1 || Integer.parseInt(colourID) > 5)) {
            Toast.makeText(this, "The card's colour ID cannot be left blank and it must be from 1 to 5.",
                    Toast.LENGTH_SHORT).show();
            proceed = false;

        } else if (typeID.isEmpty() || (Integer.parseInt(typeID) < 1 || Integer.parseInt(typeID) > 4)) {
            Toast.makeText(this, "The card's type ID cannot be left blank and it must be from 1 to 4.",
                    Toast.LENGTH_SHORT).show();
            proceed = false;

        } else if (price.isEmpty() || Double.parseDouble(price) < 0) {
            Toast.makeText(this, "The card's price cannot be left blank, nor can it be less than 0.",
                    Toast.LENGTH_SHORT).show();
            proceed = false;

        } else if (quantity.isEmpty() || Double.parseDouble(quantity) < 0) {
            Toast.makeText(this, "The card's quantity cannot be left blank, nor can it be less than 0."
                    , Toast.LENGTH_SHORT).show();
            proceed = false;
        }

        if (proceed) {

            // TODO: addCard , by creating HTTP client that send data (POST parameters) to MYSQL
            AsyncHttpClient client = new AsyncHttpClient();
            String url = "http://10.0.2.2/19039480_c302_magic/19039480_createCard.php";
            RequestParams params = new RequestParams();
            params.put("loginId", loginId);
            params.put("apikey", apikey);
            params.put("name", name);
            params.put("colourId", colourID);
            params.put("typeId", typeID);
            params.put("price", price);
            params.put("quantity", quantity);
            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Toast.makeText(CreateCardActivity_19039480.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(CreateCardActivity_19039480.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }

    @Override
   /*TODO: Click  to handle action bar items. If you specify a parent activity in AndroidManifest.xml,
    the action bar will handle clicks on the Home/Up button automatically.*/
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.menuItemAddCard) {
            // send the necessary intent data for card addition
            Intent myCreateCardActivityIntent = new Intent(this, CreateCardActivity_19039480.class);
            myCreateCardActivityIntent.putExtra("loginId", loginId);
            myCreateCardActivityIntent.putExtra("apikey", apikey);
            startActivity(myCreateCardActivityIntent);

        } else if (itemId == R.id.menuItemCardsByColour) {
            //return to the homepage by passing the relevant intent data
            Intent myMainActivityIntent = new Intent(this, MainActivity.class);
            myMainActivityIntent.putExtra("loginId", loginId);
            myMainActivityIntent.putExtra("apikey", apikey);

            startActivity(myMainActivityIntent);

        } else if (itemId == R.id.menuItemLogout) {
            //return to the LoginPage by passing the relevant intent data
            Intent myLogoutIntent = new Intent(this, LoginActivity.class);
            myLogoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Remove any relevant sharedPreferences for logging out.
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(myLogoutIntent);
            Toast.makeText(this, "You've successfully logged out.", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
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