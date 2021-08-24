package com.myapplicationdev.android.c302_p07_ps_address_book;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView lvContact;
    private ArrayList<Contact> alContact;
    private ArrayAdapter<Contact> aaContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = (ListView) findViewById(R.id.listViewContact);
    }

    //refresh with latest contact data whenever this activity resumes
    @Override
    protected void onResume() {
        super.onResume();

		//TODO: call getListOfContacts.php to retrieve all contact details
        alContact = new ArrayList<Contact>();
        aaContact = new ContactAdapter(getApplicationContext(), R.layout.contact_row, alContact);
        lvContact.setAdapter(aaContact);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/C302_P07/getListOfContacts.php", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject category = (JSONObject) response.get(i);
                        Contact c = new Contact(category.getInt("id"), category.getString("firstname"),  category.getString("lastname"),  category.getString("mobile"));
                        alContact.add(c);

                    }
                } catch (JSONException e) {

                }
                aaContact.notifyDataSetChanged();

//                super.onSuccess(statusCode, headers, response);
            }
        });


        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contact selectedContact = alContact.get(position);
                Intent i = new Intent(getBaseContext(), ViewContactDetailsActivity.class);
                i.putExtra("contact_id", selectedContact.getContactId());
                startActivity(i);

            }
        });

    }//end onResume


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
            Intent intent = new Intent(getApplicationContext(), CreateContactActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }






}