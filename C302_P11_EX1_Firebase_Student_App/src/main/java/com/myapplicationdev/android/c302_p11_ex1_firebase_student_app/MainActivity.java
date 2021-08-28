package com.myapplicationdev.android.c302_p11_ex1_firebase_student_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

     static final String TAG = "MainActivity";
     ListView lvStudent;
     ArrayList<Student> alStudent;
     ArrayAdapter<Student> aaStudent;

    // TODO: Task 1 - Declare Firebase variables

     FirebaseFirestore db;
     CollectionReference colRef;
     DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStudent = findViewById(R.id.listViewStudents);

        // TODO: Task 2: Get FirebaseFirestore instance and reference
        db = FirebaseFirestore.getInstance();

        colRef = db.collection("students");
        docRef = colRef.document("student");
        docRef.addSnapshotListener((DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) -> {
            if (e != null) {
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                Message msg = documentSnapshot.toObject(Message.class);
            }
        });


        alStudent = new ArrayList<>();
        aaStudent = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, alStudent);
        lvStudent.setAdapter(aaStudent);

        //TODO: Task 3: Get real time updates from firestore by listening to collection "students"
        //TODO: Task 4: Read from Snapshot and add into ArrayAdapter for ListView
        colRef.addSnapshotListener((QuerySnapshot querySnapshot, FirebaseFirestoreException e) -> {
            if (e != null) {
                        System.err.println("Listen failed:" + e);
                        return;
            }
            alStudent.clear();
            assert querySnapshot != null;
            for (DocumentSnapshot doc : querySnapshot) {
                        if (doc.get("name") != null && doc.get("age") != null) {
                            Student student = new Student(doc.getString("name"), Integer.parseInt(doc.getLong("age").toString()));
                            student.setId(doc.getId());
                            alStudent.add(student);
                        }
                    }
            Log.d("hi", alStudent.toString());
            aaStudent.notifyDataSetChanged();
        });


        lvStudent.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            Student student = alStudent.get(i);  // Get the selected Student
            Intent intent = new Intent(MainActivity.this, StudentDetailsActivity.class);
            intent.putExtra("StudentID", student.getId());
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
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.addStudent) {

            Intent intent = new Intent(getApplicationContext(), AddStudentActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
