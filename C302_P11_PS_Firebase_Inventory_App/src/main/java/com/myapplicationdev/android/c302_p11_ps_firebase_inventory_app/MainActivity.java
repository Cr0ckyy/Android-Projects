package com.myapplicationdev.android.c302_p11_ps_firebase_inventory_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";
    ListView lvStudent;
    ArrayList<Inventory> inventories;
    ArrayAdapter<Inventory> adapter;

    // TODO: Task 1 - Declare Firebase variables
    FirebaseFirestore fireStore;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStudent = findViewById(R.id.listViewStudents);


        //TODO: Set ArrayList, then add data to adapter, then set ListView
        inventories = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, inventories);
        lvStudent.setAdapter(adapter);


        // TODO: get fireStore collection instance
        fireStore = FirebaseFirestore.getInstance();
        collectionReference = fireStore.collection("inventory");

        // TODO: adding data into fireStore
        collectionReference.addSnapshotListener((QuerySnapshot value, FirebaseFirestoreException error) -> {
            inventories.clear();

            if (error != null) {
                Log.w(TAG, "Adding data to Firestore failed", error);
                return;
            }

            assert value != null;

            // loop through firebase documents to retrieve data and add it to a ListView
            for (QueryDocumentSnapshot documentSnapshot : value) {
                if (documentSnapshot.get("itemName") != null && documentSnapshot.get("unitCost") != null) {
                    String name = documentSnapshot.getString("itemName");
                    Long longCost = documentSnapshot.getLong("unitCost");

                    assert longCost != null;
                    int intCost = (int) longCost.longValue();

                    Inventory inventory = new Inventory(documentSnapshot.getId(), name, intCost);
                    inventories.add(inventory);
                }
            }
            // reload adapter thus refresh listview data
            adapter.notifyDataSetChanged();
        });

        lvStudent.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {

            // Get the selected Inventory data
            Inventory selectedInventory = inventories.get(i);
            String selectedInventoryID = selectedInventory.getId();

            // pass selected Inventory's itemID data to EditInventoryActivity
            Intent intent = new Intent(MainActivity.this, EditInventoryActivity.class);
            intent.putExtra("itemID", selectedInventoryID);
            startActivityForResult(intent, 1);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        // Handle action bar item clicks here
        int itemId = item.getItemId();

        if (itemId == R.id.addStudent) {
            Intent intent = new Intent(getApplicationContext(), AddInventoryActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
