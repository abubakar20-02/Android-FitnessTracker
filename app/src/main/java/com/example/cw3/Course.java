package com.example.cw3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.cw3.Services.MyService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Objects;


public class Course extends AppCompatActivity implements OnMapReadyCallback {
    private MyService.MyBinder myService = null;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    LocationManager locationManager;
    Location locationGPS;
    LatLng a;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Context context = getApplicationContext();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(Course.this, MyService.class));
//        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
//        this.bindService(new Intent(this, MyService.class), ServiceConnection,Context.BIND_AUTO_CREATE);
//        updateValuesFromBundle(savedInstanceState);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        a = new LatLng(locationGPS.getLatitude(),locationGPS.getLongitude());

    }
    //Create new service connection
    private final ServiceConnection ServiceConnection = new ServiceConnection() {
        //Once connected then create a new service binder object in service
        @Override
        public void onServiceConnected(ComponentName trackName, IBinder service) {
            Log.d("g53mdp", "NewCourse onServiceConnected");
            myService = (MyService.MyBinder) service;
        }

        //If service broken unexpectedly close the service and set the object to null
        @Override
        public void onServiceDisconnected(ComponentName trackName) {
            Log.d("g53mdp", "NewCourse onServiceDisconnected");
            myService = null;
        }
    };

    @SuppressLint("LongLogTag")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d("qwedjkhasdbkhjasduhjkbashjkdbsajhkbrdfjhasbrhjbaserhjkba","awsjedrjkawsehrdfjkbhsefhbjasehbjrfshjabdfyhujasdergydaused");
        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mMap.getUiSettings().setZoomGesturesEnabled(true);
        LatLng latLng = new LatLng(0,0);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("here");
        markerOptions.position(latLng);
        mMap.addMarker(markerOptions);
//        mMap.addMarker(new MarkerOptions().position(a).title("here"));
//        Log.d("Lat",String.valueOf(locationGPS.getLatitude()));
//        Log.d("Log",String.valueOf(locationGPS.getLongitude()));

//
//        setMap(fusedLocationClient.getCurrentLocation());
    }

//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                Course.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                Course.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        } else {
//            LocationManager locationManager = null;
//            assert false;
//
//            if (locationGPS != null) {
//                double lat = locationGPS.getLatitude();
//                double longi = locationGPS.getLongitude();
//                String latitude = String.valueOf(lat);
//                String longitude = String.valueOf(longi);
//            } else {
//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    //Update map with a drawn line of the current route, shows start point
//    private void setMap(List<LatLng> latLngs) {
//
//        if (mMap != null) {
//
//            if (!latLngs.isEmpty()) {
//                mMap.clear();
//                PolylineOptions options = new PolylineOptions().color(Color.RED).width(10).addAll(latLngs);
//                mMap.addPolyline(options);
//                mMap.addMarker(new MarkerOptions().position(latLngs.get(0)).title("Start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(latLngs.size() - 1), 15));
//            }
//            else {
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        // Set the map's camera position to the current location of the device.
//                        if (task.getResult() != null) {
//                            LatLng latLng = new LatLng(task.getResult().getLatitude(), task.getResult().getLongitude());
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                        }
//                    }
//                });
//            }
//        }
//    }

//    private void updateValuesFromBundle(Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            return;
//        }
//
//        // Update the value of requestingLocationUpdates from the Bundle.
//        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
//            requestingLocationUpdates = savedInstanceState.getBoolean(
//                    REQUESTING_LOCATION_UPDATES_KEY);
//        }
//
//        // ...
//
//        // Update UI to match restored state
//        updateUI();
//    }
//
//    private void updateUI() {
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        stopLocationUpdates();
//    }
//
//    private void stopLocationUpdates() {
//        fusedLocationClient.removeLocationUpdates(locationCallback);
//    }
//
//    private void startLocationUpdates() {
//        fusedLocationClient.getCurrentLocation();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (requestingLocationUpdates) {
//            startLocationUpdates();
//        }
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
//                requestingLocationUpdates);
//        // ...
//        super.onSaveInstanceState(outState);
//    }



}