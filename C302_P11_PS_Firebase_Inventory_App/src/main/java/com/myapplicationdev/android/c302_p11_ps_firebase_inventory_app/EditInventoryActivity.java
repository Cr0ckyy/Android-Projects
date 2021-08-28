package com.myapplicationdev.android.c302_p11_ps_firebase_inventory_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class EditInventoryActivity extends AppCompatActivity {

    static final String TAG = "StudentDetailsActivity";

    EditText etItemName, etUnitCost;
    Button btnUpdate, btnDelete;

    Inventory inventory;

    // TODO: Task 1 - Declare Firebase variables
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory);

        etItemName = findViewById(R.id.editTextName);
        etUnitCost = findViewById(R.id.editTextAge);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);

        // get the passed Inventory's itemID data from MainActivity intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("itemID");

        // TODO: Task 2: get fireStore collection instance
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("inventory");

        //TODO: Task 3: Get document reference by the Inventory's itemID
        documentReference = collectionReference.document(id);

        // TODO: add data into EditTexts for editing
        documentReference.addSnapshotListener((DocumentSnapshot value, FirebaseFirestoreException error) -> {

            if (error != null) {
                Log.w(TAG, "Adding data to Firestore failed", error);
                return;
            }
            if (value != null && value.exists()) {

                // Returns the document's POJO content/null if the document doesn't exist.
                inventory = value.toObject(Inventory.class);
                etItemName.setText(inventory != null ? inventory.getItemName() : null);
                etUnitCost.setText(String.valueOf(inventory.getUnitCost()));
            }
        });


        btnUpdate.setOnClickListener((View v) -> {

            //TODO: Task 4: Update Inventory info based on input given
            String itemName = etItemName.getText().toString();
            int unitCost = Integer.parseInt(etUnitCost.getText().toString());

            // set new info for the respective inventory data
            inventory = new Inventory(itemName, unitCost);
            documentReference.set(inventory);

            Toast.makeText(getApplicationContext(), "Student record updated successfully", Toast.LENGTH_SHORT).show();

            finish();
        });

        //TODO: Task 5: Delete Student record based on student id
        btnDelete.setOnClickListener((View v) -> {
            documentReference.delete();
            Toast.makeText(getApplicationContext(), "Student record deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        });


    }
}
