package com.myapplicationdev.android.c302_p11_ex1_firebase_student_app;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class AddStudentActivity extends AppCompatActivity {

    static final String TAG = "AddStudentActivity";

    EditText etName, etAge;
    Button btnAdd;

    // TODO: Task 1 - Declare Firebase variables
    FirebaseFirestore db;
    CollectionReference colRef;
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etName = findViewById(R.id.editTextName);
        etAge = findViewById(R.id.editTextAge);
        btnAdd = findViewById(R.id.buttonAdd);


        // TODO: Task 2: Get FirebaseFirestore instance and collection reference to "students"

        db = FirebaseFirestore.getInstance();

        colRef = db.collection("students");
        docRef = colRef.document("student");
        docRef.addSnapshotListener((@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) -> {
            if (e != null) {
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                Message msg = snapshot.toObject(Message.class);
            }
        });

        btnAdd.setOnClickListener((View v) -> {

            //TODO: Task 3: Retrieve name and age from EditText and instantiate a new Student object
            //TODO: Task 4: Add student to database and go back to main screen
            String name = etName.getText().toString();
            String age = etAge.getText().toString();

            Student student = new Student(name, Integer.parseInt(age));

            db.collection("students").add(student);


            finish();

        });


    }
}