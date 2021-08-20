package com.myapplicationdev.android.c347_l11_ex2_demo_notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    int requestCode = 123;
    int notificationID = 888;
    Button buttonNotify1, buttonNotify2, buttonNotify3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNotify1 = findViewById(R.id.buttonNotify1);
        buttonNotify2 = findViewById(R.id.buttonNotify2);
        buttonNotify3 = findViewById(R.id.buttonNotify3);


        buttonNotify1.setOnClickListener((View view) -> {

            // notificationManager alerts the user to events
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            // ensuring SDK version is greater than 0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new
                        NotificationChannel("default", "Default Channel",
                        NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("This is for default notification");
                notificationManager.createNotificationChannel(channel);
            }

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent =
                    PendingIntent.getActivity
                            (MainActivity.this, requestCode, intent,
                                    PendingIntent.FLAG_CANCEL_CURRENT);

            // Build notification
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(MainActivity.this, "default");
            builder.setContentTitle("Amazing Offer!");
            builder.setContentText("Subject");
            builder.setSmallIcon(android.R.drawable.btn_star_big_off);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(uri);
                builder.setPriority(Notification.PRIORITY_HIGH);
            }

            // TODO: This replaces the existing notification with the same ID
            Notification notification = builder.build();
            // An integer good to have, for you to programmatically cancel it
            notificationManager.notify(notificationID, notification);
            finish();
        });

        buttonNotify2.setOnClickListener((View view) -> {
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);

                channel.setDescription("This is for default notification");
                notificationManager.createNotificationChannel(channel);
            }

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity
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
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "default");
            builder.setContentTitle("Amazing Offer!");
            builder.setContentText("Subject");
            builder.setSmallIcon(android.R.drawable.btn_star_big_off);
            builder.setContentIntent(pendingIntent);
            builder.setStyle(bigText);
            builder.setAutoCancel(true);

            // TODO: This replaces the existing notification with the same ID
            Notification notification = builder.build();
            notificationManager.notify(notificationID, notification);
            finish();
        });

        buttonNotify3.setOnClickListener((View v) -> {

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
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent =
                    PendingIntent.getActivity
                    (MainActivity.this,
                            requestCode,
                            intent,
                            PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.BigPictureStyle bigPicture = new NotificationCompat.BigPictureStyle();
            bigPicture.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.sentosa)).build();

            // Build notification
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(MainActivity.this, "default");
            builder.setContentTitle("Welcome to Sentosa!");
            builder.setContentText("Singapore's premier island getaway");
            builder.setSmallIcon(android.R.drawable.btn_star_big_off);
            builder.setContentIntent(pendingIntent);
            builder.setStyle(bigPicture);
            builder.setAutoCancel(true);

            // TODO:  This replaces the existing notification with the same ID
            Notification notification = builder.build();
            notificationManager.notify(notificationID, notification);
            finish();
        });
    }
}