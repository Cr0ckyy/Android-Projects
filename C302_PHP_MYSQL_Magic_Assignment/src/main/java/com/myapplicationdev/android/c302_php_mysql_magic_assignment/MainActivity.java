package com.myapplicationdev.android.c302_php_mysql_magic_assignment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();
    // TODO: declare sharedPreferences that can be used to pass user data around the application
    static final String SHARED_PREFERENCES = "sharedPreferences";
    ListView colourListView;
    ArrayList<Colour> colourArrayList;
    ArrayAdapter<Colour> colourArrayAdapter;
    // TODO (3) Declare loginId ,  apikey and role
    String loginId;
    String apikey;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensure that internet-related services are fully granted throughout the application
        @SuppressLint("InlinedApi") String[] permissions = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };
        ActivityCompat.requestPermissions(this, permissions, 0);
        getLocalIpAddress();

        // TODO: Inserting data into the contact listView via arrayList and colourAdapter to initialize it.
        colourListView = findViewById(R.id.listViewContact);
        colourArrayList = new ArrayList<>();
        colourArrayAdapter = new ColourAdapter(this, R.layout.colour_row, colourArrayList);
        colourListView.setAdapter(colourArrayAdapter);


        // Todo: Get loginId, apikey and role from the LoginActivity
        Intent loginIntent = getIntent();
        loginId = loginIntent.getStringExtra("loginId");
        apikey = loginIntent.getStringExtra("apikey");
        role = loginIntent.getStringExtra("role");

        colourListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            //obtain the color that is being selected in Listview
            Colour selectedColour = colourArrayList.get(position);
            int selectedColourId = selectedColour.getColourId();

            // TODO:prepare user data for passing to CardActivity when a card is selected.
            // Put the following into intent:- loginId, apikey, role , colourId
            Intent cardIntent = new Intent(MainActivity.this, CardActivity_19039480.class);
            cardIntent.putExtra("loginId", loginId);
            cardIntent.putExtra("apikey", apikey);
            cardIntent.putExtra("role", role);
            cardIntent.putExtra("colourId", selectedColourId);
            startActivity(cardIntent);
        });
    }


    @Override
    // TODO: The user interaction begins after onRestoreInstanceState, onRestart, or onPause.
    protected void onResume() {
        super.onResume();

        // Clear the Colour data in ListView to reload it.
        colourArrayList.clear();

        // TODO: create HTTP client that send data (POST parameters) to MYSQL to get colour data
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url = "http://10.0.2.2/19039480_c302_magic/19039480_getColours.php";
        params.put("loginId", loginId);
        params.put("apikey", apikey);
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                getAllColours(response);
            }

        });

    }


    // TODO: Get all colors from MySQL DB
    private void getAllColours(JSONArray response) {
        try {
            Log.i("Colour Data: ", String.valueOf(response.length()));

            int i = 0;
            while (i < response.length()) {
                JSONObject categoryObj = (JSONObject) response.get(i);
                Colour fetchedColor = new Colour(
                        categoryObj.getInt("colourId"),
                        categoryObj.getString("colourName")
                );
                colourArrayList.add(fetchedColor);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // notify cardArrayAdapter to reload listview data
        colourArrayAdapter.notifyDataSetChanged();
    }

    @Override
    // TODO: Inflate the menu; this adds items to the action bar, if one is present.
    public boolean onCreateOptionsMenu(Menu menu) {

        if (role.equals("admin")) {
            getMenuInflater().inflate(R.menu.admin_menu, menu);
        } else if (role.equals("customer")) {
            getMenuInflater().inflate(R.menu.customer_menu, menu);
        }
        return true;
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
            myMainActivityIntent.putExtra("role", role);

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
