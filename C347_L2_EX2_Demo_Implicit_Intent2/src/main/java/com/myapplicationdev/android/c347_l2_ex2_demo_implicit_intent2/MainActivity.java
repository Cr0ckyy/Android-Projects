package com.myapplicationdev.android.c347_l2_ex2_demo_implicit_intent2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button buttonEmail, buttonRP;
    EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEmail = findViewById(R.id.buttonEmail);
        buttonRP = findViewById(R.id.buttonRP);
        editTextMessage = findViewById(R.id.editTextMessage);

        buttonEmail.setOnClickListener((View view) -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            String[] emailAddress = {"TestEmail@outlook.sg"};
            String subject = "Test Email from C347";
            String contents = editTextMessage.getText().toString();

            emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, contents);

            emailIntent.setType("message/rfc822");

            startActivity(Intent.createChooser(emailIntent, "Choose an email client: "));
        });

        buttonRP.setOnClickListener((View view) -> {
            Intent rpIntent = new Intent(Intent.ACTION_VIEW);
            rpIntent.setData(Uri.parse("https://www.rp.edu.sg"));
            startActivity(rpIntent);
        });

    }
}