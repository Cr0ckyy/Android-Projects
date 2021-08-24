package com.myapplicationdev.android.c347_l10_ps_enhanced_getting_my_location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    TextView coordinatesTextView;
    Button getLocationButton, removeLocationButton, checkRecordsButton;
    ToggleButton musicToggle;
    GoogleMap map;

    // singapore centre's coordinates
    LatLng singaporeCoords = new LatLng(1.3521, 103.8198);
    Marker currentLocationMarker;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: give permissions
        @SuppressLint("InlinedApi") String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        };
        ActivityCompat.requestPermissions(MainActivity.this, permissions, 0);


        coordinatesTextView = findViewById(R.id.lastCoordinatesTextView);
        getLocationButton = findViewById(R.id.getLocationButton);
        removeLocationButton = findViewById(R.id.removeLocationButton);
        checkRecordsButton = findViewById(R.id.checkRecordsButton);
        musicToggle = findViewById(R.id.musicToggle);

        musicToggle.setOnCheckedChangeListener((CompoundButton compoundButton, boolean checked) -> {
            Intent i = new Intent(MainActivity.this, MusicService.class);
            if (checked) {
                startService(i);
            } else {
                stopService(i);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.mapView);

        assert mapFragment != null;
        mapFragment.getMapAsync((GoogleMap googleMap) -> {
            map = googleMap;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(singaporeCoords, 10));
            UiSettings uiSettings = map.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);
        });

        removeLocationButton.setEnabled(false);


        getLocationButton.setOnClickListener((View view) -> {
            Intent bindIntent = new Intent(MainActivity.this, LocationService.class);
            bindService(bindIntent, connection, BIND_AUTO_CREATE);
            getLocationButton.setEnabled(false);
            removeLocationButton.setEnabled(true);
        });

        removeLocationButton.setOnClickListener((View view) -> {
            unbindService(connection);
            getLocationButton.setEnabled(true);
            removeLocationButton.setEnabled(false);
        });

        checkRecordsButton.setOnClickListener((View view) -> {
            Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
            startActivity(intent);
        });
    }

    LocationService.LocationBinder binder;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (LocationService.LocationBinder) iBinder;
            binder.start(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("disconnected");
        }
    };

    void updateLastLocation(Location location) {
        coordinatesTextView.setText(toStringCoordinates(location));

        if (currentLocationMarker == null) {
            MarkerOptions options = new MarkerOptions()
                    .position(coordinatesFrom(location))
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            currentLocationMarker = map.addMarker(options);
            map.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(coordinatesFrom(location), 16));
        } else {
            currentLocationMarker.setPosition(coordinatesFrom(location));
            map.moveCamera(CameraUpdateFactory
                    .newLatLng(coordinatesFrom(location)));
        }
    }

    String toStringCoordinates(Location location) {
        return "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
    }

    LatLng coordinatesFrom(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }


}