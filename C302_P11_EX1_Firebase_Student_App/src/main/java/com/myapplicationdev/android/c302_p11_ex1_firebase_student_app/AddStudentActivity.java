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

    FirebaseFirestore fireStore;
    CollectionReference collectionReference;
    DocumentReference documentReference;
    static Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etName = findViewById(R.id.editTextName);
        etAge = findViewById(R.id.editTextAge);
        btnAdd = findViewById(R.id.buttonAdd);

        // TODO: getting fireStore collection & document References
        fireStore = FirebaseFirestore.getInstance();
        collectionReference = fireStore.collection("students");
        documentReference = collectionReference.document("student");

        documentReference.addSnapshotListener((@Nullable DocumentSnapshot snapshot,
                                               @Nullable FirebaseFirestoreException e) -> {
            if (e != null) {
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                // Returns the document's POJO content/null if the document doesn't exist.
                message = snapshot.toObject(Message.class);
            }
        });

        btnAdd.setOnClickListener((View v) -> {

            // TODO: setting data for FireStore DocumentReference to add data
            String name = etName.getText().toString();
            String age = etAge.getText().toString();
            Student student = new Student(name, Integer.parseInt(age));
            fireStore.collection("students").add(student);

            finish();
        });
    }
}
