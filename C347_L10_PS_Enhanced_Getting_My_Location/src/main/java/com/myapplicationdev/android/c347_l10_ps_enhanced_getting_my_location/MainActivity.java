package com.myapplicationdev.android.c347_l10_ps_enhanced_getting_my_location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    Button btnStartDetector, btnStopDetector, btnRecords;
    TextView tvLatitude, tvLongitude;
    GoogleMap map;
    FusedLocationProviderClient client;
    LocationCallback mLocationCallback;
    String folderLocation;
    ToggleButton tbMusic;
    FragmentManager fm;
    SupportMapFragment mapFragment;
    File targetFile, folder;
    UiSettings ui;
    Marker currLocation;
    Intent i;
    int permissionCheck, permissionCheck_Coarse, permissionCheck_Fine;
    LocationRequest locationRequest;
    FileWriter fileWriter;
    Location data;
    LatLng coordinates;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartDetector = findViewById(R.id.btnStartDetector);
        btnStopDetector = findViewById(R.id.btnStopDetector);
        btnRecords = findViewById(R.id.btnRecords);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        tbMusic = findViewById(R.id.tbMusic);
        checkPermission();

        fm = getSupportFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        folderLocation = getFilesDir().getAbsolutePath() + "/Folder";
        targetFile = new File(folderLocation, "data.txt");


        mLocationCallback = new LocationCallback() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                data = locationResult.getLastLocation();
                lat = data.getLatitude();
                lng = data.getLongitude();
                coordinates = new LatLng(lat, lng);

                assert mapFragment != null;
                mapFragment.getMapAsync(googleMap -> {

                    map = googleMap;
                    tvLatitude.setText("Latitude: " + lat);
                    tvLongitude.setText("Longitude: " + lng);


                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                    map.clear();

                    currLocation = map.addMarker(new
                            MarkerOptions()
                            .position(coordinates)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                    ui = map.getUiSettings();
                    ui.setCompassEnabled(true);
                    ui.setZoomControlsEnabled(true);
                    ui.setMyLocationButtonEnabled(true);

                    folder = new File(folderLocation);
                    if (!folder.exists()) {
                        boolean result = folder.mkdir();
                        if (result) {
                            Log.d("File Read/Write", "Folder created");
                        }
                    }
                    try {
                        fileWriter = new FileWriter(targetFile, true);
                        fileWriter.write(lat + ", " + lng + "\n");
                        fileWriter.flush();
                        fileWriter.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                Toast.makeText(MainActivity.this, "New Location Detected\n" + "Lat: " + lat + ", " + " Lat: " + lng, Toast.LENGTH_SHORT).show();
            }
        };

        btnStartDetector.setOnClickListener((View view) -> {
            checkPermission();

            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(5000);
            locationRequest.setSmallestDisplacement(100);
            client.requestLocationUpdates(locationRequest, mLocationCallback, null);

            i = new Intent(MainActivity.this, DetectorService.class);
            startService(i);
            Toast.makeText(MainActivity.this, "The detector has been activated.", Toast.LENGTH_SHORT).show();

        });

        btnStopDetector.setOnClickListener((View view) -> {
            client.removeLocationUpdates(mLocationCallback);
            map.clear();

            i = new Intent(MainActivity.this, DetectorService.class);
            stopService(i);
            Toast.makeText(MainActivity.this, "The detector has been turned off.", Toast.LENGTH_SHORT).show();

        });

        btnRecords.setOnClickListener((View view) -> {
            i = new Intent(MainActivity.this, RecordsActivity.class);
            startActivity(i);
        });

        tbMusic.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            permissionCheck = PermissionChecker.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                Log.i("permission", "Permission not granted");
                return;
            }
            if (isChecked) {
                tbMusic.setTextOn("MUSIC OFF");
                startService(new Intent(MainActivity.this, MusicService.class));
            } else {
                tbMusic.setTextOff("MUSIC ON");
                stopService(new Intent(MainActivity.this, MusicService.class));
            }
        });

    }

    private void checkPermission() {
        permissionCheck_Coarse = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionCheck_Fine = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED) {
            System.out.println("ACCESS_COARSE_LOCATION & ACCESS_FINE_LOCATION have been granted permission.");
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btnStartDetector.performClick();
                Toast.makeText(MainActivity.this, "The location update has begun.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "No permission has been granted.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}