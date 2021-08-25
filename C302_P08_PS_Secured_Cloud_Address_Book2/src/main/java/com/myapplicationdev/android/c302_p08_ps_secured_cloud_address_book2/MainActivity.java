package com.myapplicationdev.android.c302_p08_ps_secured_cloud_address_book2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ListView lvContact;
    ArrayList<Contact> alContact;
    ArrayAdapter<Contact> aaContact;

    // TODO (3) Declare loginId and apikey
    String loginId;
    String apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact =  findViewById(R.id.listViewContact);
        alContact = new ArrayList<>();
        aaContact = new ContactAdapter(this, R.layout.contact_row, alContact);
        lvContact.setAdapter(aaContact);

        Intent loginIntent = getIntent();
        // TODO (4) Get loginId and apikey from the previous Intent
        loginId = loginIntent.getStringExtra("loginId");
        apikey = loginIntent.getStringExtra("apikey");

        lvContact.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            Contact selectedContact = alContact.get(position);

            // TODO (7) When a contact is selected, create an Intent to View Contact Details
            // Put the following into intent:- contact_id, loginId, apikey
            Intent detailsIntent = new Intent(MainActivity.this, ViewContactDetailsActivity.class);
            detailsIntent.putExtra("loginId", loginId);
            detailsIntent.putExtra("apikey", apikey);
            detailsIntent.putExtra("contact_id", selectedContact.getContactId());

            startActivity(detailsIntent);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        alContact.clear();

        // TODO (5) Call getListOfContacts.php to update the MainActivity's contact list.
        // What is the web service URL?
        // What is the HTTP method?
        // What parameters need to be provided?

        // TODO: create HTTP client that send data (POST parameters) to MYSQL
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://10.0.2.2/C302_P08_SecuredCloudAddressBook/getListOfContacts.php";
        RequestParams params = new RequestParams();
        params.put("loginId", loginId);
        params.put("apikey", apikey);

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                getAllContacts(response);
            }

        });

    }

    // TODO (6) Using AsyncHttpClient for getListOfContacts.php, get all contacts from the results and show in the list
    void getAllContacts(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject category = (JSONObject) response.get(i);
                Contact contact = new Contact(
                        category.getInt("id"),
                        category.getString("firstname"),
                        category.getString("lastname"),
                        category.getString("mobile")
                );
                alContact.add(contact);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        aaContact.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_add) {

            // TODO (8) Create an Intent to Create Contact
            // Put the following into intent:- loginId, apikey

            Intent createIntent = new Intent(MainActivity.this, CreateContactActivity.class);
            createIntent.putExtra("loginId", loginId);
            createIntent.putExtra("apikey", apikey);

            startActivity(createIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
