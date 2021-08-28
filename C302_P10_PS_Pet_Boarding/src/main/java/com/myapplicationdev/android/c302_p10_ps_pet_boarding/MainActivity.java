package com.myapplicationdev.android.c302_p10_ps_pet_boarding;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView textViewFirebaseOutput;
    Button btnSend;
    EditText etName, etNumberOfDays, etBoardingDate;
    Spinner petTypeSpinner;
    CheckBox cbHasBeenVaccinated;
    Date selectedDate;
    String[] petTypes;
    ArrayAdapter<String> adapter;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    DocumentReference documentReference;
    static Pet myPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewFirebaseOutput = findViewById(R.id.textViewFirebaseOutput);
        etName = findViewById(R.id.etName);
        etNumberOfDays = findViewById(R.id.etNumberOfDays);
        petTypeSpinner = findViewById(R.id.spPetType);
        etBoardingDate = findViewById(R.id.etBoardingDate);
        cbHasBeenVaccinated = findViewById(R.id.cbHasBeenVaccinated);
        btnSend = findViewById(R.id.btnSend);

        //TODO: Setting array, then adding data to adapter, then setting petTypeSpinner
        petTypes = new String[]{"cat", "dog", "cow"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petTypeSpinner.setAdapter(adapter);

        // TODO: getting fireStore collection & document References
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("PetBoardings");
        documentReference = collectionReference.document("Pet");

        // TODO: adding data into fireStore
        documentReference.addSnapshotListener((DocumentSnapshot value, FirebaseFirestoreException error) -> {
            if (error != null) {
                error.printStackTrace();
                return;
            }

            if (value != null && value.exists()) {
                // Returns the document's POJO content/null if the document doesn't exist.
                myPet = value.toObject(Pet.class);
                assert myPet != null;
                @SuppressLint("DefaultLocale") String myFirebaseOutput = String.format("" +
                                "Name: %s\n" +
                                "Number of days: %d\n" +
                                "Pet Type: %s\n" +
                                "Has been vaccinated: %b\n" +
                                "Selected Date: %s\n",
                        myPet.getName(),
                        myPet.getNumDays(),
                        myPet.getPetType(),
                        myPet.isVaccinated(),
                        myPet.getBoardDate()

                );
                textViewFirebaseOutput.setText(myFirebaseOutput);
            } else {
                Toast.makeText(getApplicationContext(),
                        "The value provided by Firebase Firestore is null and void."
                        , Toast.LENGTH_SHORT).show();
            }


        });
        // TODO: DatePicker Dialog set up
        SingleDateAndTimePickerDialog.Builder dialogDatePicker = new SingleDateAndTimePickerDialog.Builder(this)
                .bottomSheet()
                .curved()
                .displayMinutes(true)
                .displayHours(true)
                .displayDays(false)
                .displayMonth(true)
                .displayYears(true)
                .displayDaysOfMonth(true)
                .title("Boarding Date") // dialogDatePicker title
                .listener((Date date) -> {
                    selectedDate = date;
                    etBoardingDate.setText(selectedDate.toString());
                });
        etBoardingDate.setOnClickListener((View v) -> dialogDatePicker.display());

        btnSend.setOnClickListener(this::sendOnClick);
    }

    void sendOnClick(View v) {

        // TODO: setting data for FireStore DocumentReference to add data
        Pet pet = new Pet(
                etName.getText().toString(),
                Integer.parseInt(etNumberOfDays.getText().toString()),
                petTypes[petTypeSpinner.getSelectedItemPosition()],
                selectedDate,
                cbHasBeenVaccinated.isChecked()
        );
        documentReference.set(pet);

    }
}