package com.myapplicationdev.android.c347_l9_ex1_demo_locationdetection;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    Button btnGetLastLocation, btnGetLocationUpdate, btnRemoveLocationUpdates;
    FusedLocationProviderClient locationClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetLastLocation = findViewById(R.id.btnGetLastLocation);
        btnGetLocationUpdate = findViewById(R.id.btnGetLocationUpdate);
        btnRemoveLocationUpdates = findViewById(R.id.btnRemoveLocationUpdate);

        // the main entry point for location services integration
        locationClient = LocationServices.getFusedLocationProviderClient(this);

        // if permission is not granted
        if (!checkPermission()) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }

        // TODO: used to receive API notifications when the device's location has changed/cannot be determined.
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {

                // Returns the most recent location found in this result
                Location currentLocation = locationResult.getLastLocation();

                String toastMsg = String.format("New Location found:\nLatitude: %s\nLongitude: %s"
                        , currentLocation.getLatitude(), currentLocation.getLongitude());
                Toast.makeText(MainActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
            }
        };

        // TODO: get an update on the location
        btnGetLocationUpdate.setOnClickListener((View view) -> {

            // Runtime permission check
            if (checkPermission()) {

                // Begin the detection process by requesting a service quality
                // for location updates from the API.
                locationRequest = new LocationRequest();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // Set the priority of the request.
                locationRequest.setInterval(10000); // Set the millisecond interval for active location updates.
                locationRequest.setFastestInterval(5000); // Set the fastest interval for location updates explicitly in milliseconds.
                locationRequest.setSmallestDisplacement(100); // Set the minimum distance in meters between location updates.
                locationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            } else {
                String toastMsg = "Location information cannot be retrieved because permission has not been granted.";
                Toast.makeText(MainActivity.this, toastMsg, Toast.LENGTH_SHORT).show();

                // Activity Helper requests permissions to be granted to this application
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            }
        });

        // remove the location updates
        btnRemoveLocationUpdates.setOnClickListener((View view) -> {

            // All location updates for the given location result listener are removed.
            locationClient.removeLocationUpdates(locationCallback);

            String toastMsg = "Location updates were successfully removed.";
            Toast.makeText(MainActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
        });

        // get the most recent location
        btnGetLastLocation.setOnClickListener((View view) -> {
            if (checkPermission()) {

                // Returns the most recent location that is currently available.
                Task<Location> currentLocation = locationClient.getLastLocation();

                // Represents an asynchronous operation
                currentLocation.addOnSuccessListener(MainActivity.this, (Location location) -> {

                    String toastMsg;
                    if (location != null) {
                        toastMsg = String.format("Last Location found\nLatitude: %s\nLongitude: %s"
                                , location.getLatitude(), location.getLongitude());
                    } else {
                        toastMsg = "No last known location found.";
                    }
                    Toast.makeText(MainActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
                });

            } else {
                String toastMsg = "Permission not granted to retrieve location info.";
                Toast.makeText(MainActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            }

        });
    }

    private boolean checkPermission() {

        int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED
                || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED;
    }
}