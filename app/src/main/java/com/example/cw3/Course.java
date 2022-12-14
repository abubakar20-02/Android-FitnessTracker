package com.example.cw3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.cw3.Services.MyService;
import com.example.cw3.databinding.ActivityCourseBinding;
import com.example.cw3.viewmodels.CourseVM;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


//https://www.womanandhome.com/health-wellbeing/fitness/calories-burned-walking/
//https://www.geeksforgeeks.org/how-to-build-a-step-counting-application-in-android-studio/

public class Course extends AppCompatActivity implements OnMapReadyCallback {

    private MyService.MyBinder myService = null;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationClient;
    private ActivityCourseBinding activityNewCourseBinding;
    private GoogleMap mMap;
    private CourseVM model;
    int time=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(CourseVM.class);
        activityNewCourseBinding = ActivityCourseBinding.inflate(LayoutInflater.from(this));
        activityNewCourseBinding.setViewmodel(model);
        setContentView(R.layout.activity_course);
        Objects.requireNonNull(getSupportActionBar()).hide();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(new Intent(Course.this, MyService.class));
//            myService.startGPS();
        }
        setContentView(activityNewCourseBinding.getRoot());


        model.setIsStarted(true);
        model.setIsFinished(false);
        this.bindService(new Intent(this, MyService.class), ServiceConnection, 0);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

//        updateValuesFromBundle(savedInstanceState);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction("Location");
        filter.addAction("Time");
        registerReceiver(receiver, filter);
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

        // code to stop map
        activityNewCourseBinding.StopButton.setOnClickListener(v -> {
            model.setIsFinished(true);
            finish();
            Intent myService = new Intent(Course.this, MyService.class);
            stopService(myService);
//            courseFinished();
        });

    }

    //Create new service connection
    private ServiceConnection ServiceConnection = new ServiceConnection() {
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

    //When activity destroyed remove current observers, unregister the broadcast receiver and unbind the service
    @Override
    protected void onDestroy() {
        Log.d("g53mdp", "NewCourse onDestroy");
        unregisterReceiver(receiver);
        if (ServiceConnection != null) {
            unbindService(ServiceConnection);
            ServiceConnection = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("g53mdp", "NewCourse onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("g53mdp", "NewCourse onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d("g53mdp", "NewCourse onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("g53mdp", "NewCourse onStop");
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(false);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setOnCameraChangeListener(cameraPosition -> setMap(model.getLocations()));
    }




    // maybe add line once walking complete.
    //Update map with a drawn line of the current route, shows start point
    private void setMap(List<LatLng> latLngs) {
        Log.d("size",String.valueOf(latLngs.size()));
        Double distance =0.0;
        if (mMap != null) {
//                mMap.addMarker(new MarkerOptions().position(latLngs.get(0)).title("Start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            if (!latLngs.isEmpty()) {
                mMap.clear();
                PolylineOptions options = new PolylineOptions().color(Color.RED).width(10).addAll(latLngs);
                mMap.addPolyline(options);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(latLngs.size() - 1), 15));
//                distance = CalculationByDistance(latLngs.get(latLngs.size()-1),latLngs.get(latLngs.size()))+distance;
                Double avgSpeed= (CalculationByDistance(latLngs.get(0),latLngs.get(latLngs.size()-1))/time)*3600;
                Log.d("distance",Double.toString(distance));
                Log.d("Speed",Double.toString(avgSpeed));
            }
            else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        if (task.getResult() != null) {
                            LatLng latLng = new LatLng(task.getResult().getLatitude(), task.getResult().getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        }
                    }
                });
            }
        }
    }

    // https://stackoverflow.com/questions/14394366/find-distance-between-two-points-on-map-using-google-map-api-v2
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    //Create Broadcast Receiver to get location, time and notification button press updates
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
                switch (action) {
                    case "Location":
                        //If intent is location then save the given points to course object
                        double latitude = intent.getDoubleExtra("Latitude", 0.0);
                        double longitude = intent.getDoubleExtra("Longitude", 0.0);
                        Log.d("sadjhkash","asjdkhndhjkasdkjnbasdk");
                        model.savePoints(new LatLng(latitude, longitude));
//                        if (model.getLocations().size() == 1) {
//                            weatherByLocation(String.valueOf(latitude), String.valueOf(longitude));
//                        }
                        break;
                    case "Time":
                        //If intent is time then save the time to course object and update the notification
                        time = intent.getIntExtra("Timer", 0);
//                        model.setTime(time);
                }
            }

    };

}