package com.myapplicationdev.android.c347_l10_ps_enhanced_getting_my_location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileWriter;

public class LocationService extends Service {

    LocationBinder binder = new LocationBinder();
    FusedLocationProviderClient client;
    LocationCallback locationCallback;

    class LocationBinder extends Binder {
        //Location lastLocation;
        //LocationService locationService = LocationService.this;
        void start(MainActivity mainActivity) {

            //  TODO: 1.Folder creation
            String folderLocation = getFilesDir().getAbsolutePath() + "/LocationLogs";
            File folder = new File(folderLocation);


            if (!folder.exists()) {

                //  TODO: 2.Making folder directory
                boolean result = folder.mkdir();
                if (result) {
                    Log.d("File read/write", "Folder Created");
                } else {
                    Toast.makeText(getApplicationContext(), "folder creation FAILED!!", Toast.LENGTH_SHORT).show();
                }
            }
            //  TODO: create a "log.txt"
            File locationLog = new File(folderLocation, "log.txt");

            //  TODO: create Location Request
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(0); // in ms
            locationRequest.setSmallestDisplacement(0);

            //  TODO: getting the locationResult
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    Location lastLocation = locationResult.getLastLocation();

                    if (mainActivity != null) {
                        mainActivity.updateLastLocation(lastLocation);
                    }


                    try {
                        // TODO: writing coordinate data into the locationLog
                        FileWriter writer = new FileWriter(locationLog, true);
                        writer.write(toStringCoordinates(lastLocation));
                        writer.flush();
                        writer.close();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Fail to log location", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            };


            //  TODO: create Location Client
            client = LocationServices.getFusedLocationProviderClient(getApplicationContext());

            if (checkLocationPermission()) {
                //  TODO: request Location Updates from Location Client
                client.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                failedPermissionToast();
            }
        }
    }

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private boolean checkLocationPermission() {
        int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                LocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION);

        @SuppressLint("InlinedApi") int permissionCheck_Bg = ContextCompat.checkSelfPermission(
                LocationService.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        return permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED
                || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED ||
                permissionCheck_Bg == PermissionChecker.PERMISSION_GRANTED
                ;
    }

    private void failedPermissionToast() {
        Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        if (checkLocationPermission()) {
            client.removeLocationUpdates(locationCallback);
        } else {
            failedPermissionToast();
        }
        super.onDestroy();
    }

    private String toStringCoordinates(Location location) {
        return location.getLatitude() + ", " + location.getLongitude() + "\n";
    }
}