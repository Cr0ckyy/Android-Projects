package com.myapplicationdev.android.c347_l11_ex2_demo_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    int requestCode = 123;
    int notificationID = 888;
    Button btnNotify1, btnNotify2, btnNotify3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotify1 = findViewById(R.id.btnNotify1);
        btnNotify2 = findViewById(R.id.btnNotify2);
        btnNotify3 = findViewById(R.id.btnNotify3);

        btnNotify1.setOnClickListener(v -> {

            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new
                        NotificationChannel("default", "Default Channel",
                        NotificationManager.IMPORTANCE_DEFAULT);

                channel.setDescription("This is for default notification");
                notificationManager.createNotificationChannel(channel);
            }

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity
                    (MainActivity.this, requestCode, intent,
                            PendingIntent.FLAG_CANCEL_CURRENT);

            // Build notification
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(MainActivity.this, "default");
            builder.setContentTitle("Amazing Offer!");
            builder.setContentText("Subject");
            builder.setSmallIcon(android.R.drawable.btn_star_big_off);
            builder.setContentIntent(pIntent);
            builder.setAutoCancel(true);

            Notification n = builder.build();

            // An integer good to have, for you to programmatically cancel it
            notificationManager.notify(notificationID, n);

            finish();
        });


        btnNotify2.setOnClickListener(v -> {

            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new
                        NotificationChannel("default", "Default Channel",
                        NotificationManager.IMPORTANCE_DEFAULT);

                channel.setDescription("This is for default notification");
                notificationManager.createNotificationChannel(channel);
            }

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity
                    (MainActivity.this, requestCode,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.BigTextStyle bigText = new
                    NotificationCompat.BigTextStyle();
            bigText.setBigContentTitle("Big Text – Long Content");
            bigText.bigText("This is one big text" +
                    " - A quick brown fox jumps over a lazy brown dog " +
                    "\nLorem ipsum dolor sit amet, sea eu quod des");
            bigText.setSummaryText("Reflection Journal?");

            // Build notification
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(MainActivity.this, "default");
            builder.setContentTitle("Amazing Offer!");
            builder.setContentText("Subject");
            builder.setSmallIcon(android.R.drawable.btn_star_big_off);
            builder.setContentIntent(pIntent);
            builder.setStyle(bigText);
            builder.setAutoCancel(true);

            Notification n = builder.build();

            // This replaces the existing notification with the same ID
            notificationManager.notify(notificationID, n);
            finish();
        });


        btnNotify3.setOnClickListener(v -> {

            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new
                        NotificationChannel("default", "Default Channel",
                        NotificationManager.IMPORTANCE_DEFAULT);

                channel.setDescription("This is for default notification");
                notificationManager.createNotificationChannel(channel);
            }

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity
                    (MainActivity.this, requestCode,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.BigPictureStyle bigPicture = new NotificationCompat.BigPictureStyle();
            bigPicture.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.sentosa)).build();

            // Build notification
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(MainActivity.this, "default");
            builder.setContentTitle("Welcome to Sentosa!");
            builder.setContentText("Singapore's premier island getaway");
            builder.setSmallIcon(android.R.drawable.btn_star_big_off);
            builder.setContentIntent(pIntent);
            builder.setStyle(bigPicture);
            builder.setAutoCancel(true);

            Notification n = builder.build();

            // This replaces the existing notification with the same ID
            notificationManager.notify(notificationID, n);
            finish();
        });


    }
}
