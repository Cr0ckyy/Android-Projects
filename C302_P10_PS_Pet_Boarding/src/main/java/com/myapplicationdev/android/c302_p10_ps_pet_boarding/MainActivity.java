package com.myapplicationdev.android.c302_p10_ps_pet_boarding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etNumOfDays, etBoardingDate;
    private Spinner spinAnimal;
    private CheckBox checkVacc;
    private Button btnRequest;


    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etName = findViewById(R.id.etName);
        etNumOfDays = findViewById(R.id.etNumOfDays);
        etBoardingDate = findViewById(R.id.etBoardingDate);
        spinAnimal = findViewById(R.id.spinAnimal);
        checkVacc = findViewById(R.id.checkVacc);

        btnRequest = findViewById(R.id.btnRequest);

        // Set the spinner for the animal the user selects
        ArrayAdapter<String> animalAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.animals));
        animalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAnimal.setAdapter(animalAdapter);

        db = FirebaseFirestore.getInstance();
        colRef = db.collection("PetBoardings");
        docRef = colRef.document("PetBoarding");


        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddOnClick(v);
            }
        });


    }

    private void btnAddOnClick(View v) {
        String name = etName.getText().toString();
        int numOfDays = Integer.parseInt(etNumOfDays.getText().toString());
        String boardingDate = etBoardingDate.getText().toString();
        String selectedAnimal = spinAnimal.getSelectedItem().toString();
        boolean vaccinated = checkVacc.isChecked();

        PetBoarding pb = new PetBoarding(boardingDate, name, numOfDays, selectedAnimal, vaccinated);
        docRef.set(pb);

    }
}