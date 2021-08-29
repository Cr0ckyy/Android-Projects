package com.myapplicationdev.android.c302_p11_ex1_firebase_student_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;


public class StudentDetailsActivity extends AppCompatActivity {

    static final String TAG = "StudentDetailsActivity";

    EditText etName, etAge;
    Button btnUpdate, btnDelete;

    Student student;

    // TODO: Task 1 - Declare Firebase variables
    FirebaseFirestore db;
    CollectionReference colRef;
    DocumentReference docRef;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        etName = findViewById(R.id.editTextName);
        etAge = findViewById(R.id.editTextAge);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("StudentID");

        // TODO: Task 2: Get FirebaseFirestore instance
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

        //TODO: Task 3: Get document reference by the student's id and set the name and age to EditText
        DocumentReference docRef = colRef.document(id);
        docRef.get().addOnCompleteListener((@NonNull Task<DocumentSnapshot> task) -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                etName.setText(document.getString("name"));
                etAge.setText(Objects.requireNonNull(document.getLong("age")).toString());
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                } else {
                    Log.d(TAG, "No such document data");
                }
            } else {
                Log.d(TAG, "Error Message:", task.getException());
            }
        });

        btnUpdate.setOnClickListener((View v) -> {
            //TODO: Task 4: Update Student record based on input given
            String name = etName.getText().toString();
            String age = etAge.getText().toString();
            Student newStudent = new Student(name, Integer.parseInt(age));
            colRef.document(id).set(newStudent);

            Toast.makeText(getApplicationContext(), "Student record updated successfully", Toast.LENGTH_SHORT).show();

            finish();
        });


        btnDelete.setOnClickListener((View v) -> {
            //TODO: Task 5: Delete Student record based on student id
            colRef.document(id).delete()
                    .addOnSuccessListener((Void aVoid) ->
                            Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                    .addOnFailureListener((@NonNull Exception e)
                            -> Log.w(TAG, "Error deleting document", e));
            Toast.makeText(getApplicationContext(), "Student record deleted successfully",
                    Toast.LENGTH_SHORT).show();

            finish();
        });


    }
}