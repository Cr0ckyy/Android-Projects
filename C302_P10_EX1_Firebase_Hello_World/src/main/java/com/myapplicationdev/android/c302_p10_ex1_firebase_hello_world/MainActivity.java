package com.myapplicationdev.android.c302_p10_ex1_firebase_hello_world;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class MainActivity extends AppCompatActivity {

    TextView tvTitle;
    EditText etTitle;
    TextView tvDate;
    EditText etDate;
    Button buttonAdd;
    FirebaseFirestore fireStore;
    CollectionReference collectionReference;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.textViewTitle);
        etTitle = findViewById(R.id.editTextTitle);
        tvDate = findViewById(R.id.textViewDate);
        buttonAdd = findViewById(R.id.buttonAdd);
        etDate = findViewById(R.id.editTextDate);


        buttonAdd.setOnClickListener(this::btnAddOnClick);

        // TODO: getting fireStore collection & document References
        fireStore = FirebaseFirestore.getInstance();
        collectionReference = fireStore.collection("dates");
        documentReference = collectionReference.document("toDoItem");

        // TODO: adding data into fireStore
        documentReference.addSnapshotListener((DocumentSnapshot value, FirebaseFirestoreException error) -> {
            if (error != null) {
                return;
            }
            if (value != null && value.exists()) {
                // Returns the document's POJO content/null if the document doesn't exist.
                Date msg = value.toObject(Date.class);
                assert msg != null;
                tvTitle.setText(msg.getTitle());
                tvDate.setText(msg.getDate());
            }
        });
    }

    void btnAddOnClick(View v) {
        String text = etTitle.getText().toString();
        String date = etDate.getText().toString();

        Date msg = new Date(text, date);
        // TODO: setting data for FireStore DocumentReference to add data
        documentReference.set(msg);

    }
}
