package com.myapplicationdev.android.c347_l11_ps_task_manager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ScheduledNotificationReceiver extends BroadcastReceiver {

    int reqCode = 12345;
    String name, description;
    NotificationManager notificationManager;
    Intent i;
    PendingIntent pendingIntent;
    Bitmap bitmap;
    Notification notification;

    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    public void onReceive(Context context, Intent intent) {
        name = intent.getExtras().getString("name");
        description = intent.getExtras().getString("description");

        notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        i = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context, reqCode,
                i, PendingIntent.FLAG_CANCEL_CURRENT);

        // TODO: bigText
//      NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//      bigText.setBigContentTitle(name + " (bigtext)");
//      bigText.bigText(description + " (bigtext)\n");

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rare_penguin); // Bitmap for bigPicture

        // TODO:  bigPicture
        NotificationCompat.BigPictureStyle bigPicture = new NotificationCompat.BigPictureStyle();
        bigPicture.bigPicture(bitmap);
        bigPicture.bigLargeIcon(null);
        bigPicture.setBigContentTitle("Task: " + name);
        bigPicture.setSummaryText("Description: " + description + "\n");

        // TODO:  Build notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "default");
        notificationBuilder.setContentTitle("Task Manager Reminder");
        notificationBuilder.setContentText(name);
        notificationBuilder.setSmallIcon(android.R.drawable.ic_dialog_info);
        notificationBuilder.setContentIntent(pendingIntent);
//      notificationBuilder.setStyle(bigText); // bigText (expandable)
        notificationBuilder.setStyle(bigPicture); // bigPicture
        notificationBuilder.setLights(0xFFFF0000, 100, 100); // LED
//      notificationBuilder.setLights(Color.RED, 3000, 3000); // LED (using Color class)
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL); // Sound
        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}); // Vibration
        notificationBuilder.setAutoCancel(true);

        notification = notificationBuilder.build();
        notificationManager.notify(123, notification);


    }
}