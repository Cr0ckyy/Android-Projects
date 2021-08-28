package com.myapplicationdev.android.c302_p11_ps_firebase_inventory_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class AddInventoryActivity extends AppCompatActivity {

    static final String TAG = "AddStudentActivity";

    EditText etItemName, etUnitCost;
    Button btnAdd;

    // TODO: Task 1 - Declare Firebase variables
    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        etItemName = (EditText) findViewById(R.id.editTextName);
        etUnitCost = (EditText) findViewById(R.id.editTextAge);
        btnAdd = (Button) findViewById(R.id.buttonAdd);


        // TODO: Task 2: Get Firestore collection reference to "inventory"
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("inventory");

        btnAdd.setOnClickListener((View v) -> {

            //TODO: Task 3: Retrieve name and age from EditText and instantiate a new Student object
            //TODO: Task 4: Add student to database and go back to main screen
            String name = etItemName.getText().toString();
            int age = Integer.parseInt(etUnitCost.getText().toString());

            Inventory inventory = new Inventory(name, age);
            collectionReference.add(inventory);

            finish();

        });


    }
}
