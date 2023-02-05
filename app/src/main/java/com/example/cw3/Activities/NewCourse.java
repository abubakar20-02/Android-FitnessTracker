package com.example.cw3.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.cw3.R;
import com.example.cw3.Services.TrackingService;
import com.example.cw3.databinding.ActivityCourseBinding;
import com.example.cw3.viewmodels.NewCourseVM;
import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.google.maps.android.SphericalUtil;

import java.util.List;
import java.util.Objects;

public class NewCourse extends AppCompatActivity implements OnMapReadyCallback {

    private TrackingService.MyBinder myService = null;
    private ActivityCourseBinding activityNewCourseBinding;
    private GoogleMap mMap;
    private NewCourseVM newCourseVM;
    int time=0;
    double distance = 0.0;
    double InitialDistance = 0.0;
    private boolean updated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newCourseVM = new ViewModelProvider(this).get(NewCourseVM.class);
        activityNewCourseBinding = ActivityCourseBinding.inflate(LayoutInflater.from(this));
        setContentView(activityNewCourseBinding.getRoot());
        activityNewCourseBinding.setViewmodel(newCourseVM);
        Objects.requireNonNull(getSupportActionBar()).hide();

        newCourseVM.getSize().observe(this, integer -> {
            // set course ID as the count of the table + 1
            newCourseVM.setCourseID(integer +1);
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(new Intent(NewCourse.this, TrackingService.class));
        }

        this.bindService(new Intent(this, TrackingService.class), ServiceConnection, 0);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        newCourseVM.setDate();

        IntentFilter filter = new IntentFilter();
        filter.addAction("Location");
        filter.addAction("Time");
        filter.addAction("Stop");
        registerReceiver(receiver, filter);

        this.bindService(new Intent(NewCourse.this, TrackingService.class), ServiceConnection, 0);

        activityNewCourseBinding.StopButton.setOnClickListener(v -> {
            StopTracking();
        });

    }

    // https://stackoverflow.com/questions/8631095/how-to-prevent-going-back-to-the-previous-activity
    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    private void StopTracking() {
        newCourseVM.saveToDB();
        Intent myService = new Intent(NewCourse.this, TrackingService.class);
        stopService(myService);
        finish();
    }

    private ServiceConnection ServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName trackName, IBinder service) {
            Log.d("g53mdp", "NewCourse onServiceConnected");
            myService = (TrackingService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName trackName) {
            Log.d("g53mdp", "NewCourse onServiceDisconnected");
            myService = null;
        }
    };

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
        SetUpMap(newCourseVM.getLocations());
    }

    //https://stackoverflow.com/questions/24408419/how-to-draw-line-on-google-map-as-user-move-draw-line-automatically
    // update map with user movement.
    private void SetUpMap(List<LatLng> cords) {

        if (mMap != null) {

            if (!cords.isEmpty()) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(cords.get(0)).title("Start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                PolylineOptions options = new PolylineOptions().color(Color.GREEN).width(5).addAll(cords);
                mMap.addPolyline(options);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cords.get(cords.size() - 1), 15));
                if (ServiceConnection != null) {
                    myService.setMap(cords);
                }
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

    // code to compute total distance travelled.
    private void ComputeDistance(List<LatLng> cords) {

        InitialDistance=0 ;
        if(cords.size()==1){
            InitialDistance = CalculationByDistance(cords.get(0), cords.get(cords.size() - 1));
        }
        if (cords.size()>2){
            InitialDistance = CalculationByDistance(cords.get(cords.size() - 2), cords.get(cords.size() - 1));
        }
        // distance in meters
            distance = myService.getDistance();
            distance = distance + InitialDistance;
            if (myService.getDistance() == distance){
                InitialDistance=0;
            }
            myService.setDistance(distance);
    }

    // https://stackoverflow.com/questions/14394366/find-distance-between-two-points-on-map-using-google-map-api-v2/15351351#15351351
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        return SphericalUtil.computeDistanceBetween(StartP, EndP);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
                switch (action) {

                    case "Location":
                        //Code to run everytime location is updated.
                        double latitude = intent.getDoubleExtra("Latitude", 0.0);
                        double longitude = intent.getDoubleExtra("Longitude", 0.0);
                        newCourseVM.saveCords(new LatLng(latitude, longitude));

                        // ensure service is running to compute distance.
                        if(myService!= null) {
                            ComputeDistance(myService.getMap());
                        }
                        updated = true;
                        break;

                    case "Time":
                        // time is used to update google maps every 1 second
                        time = intent.getIntExtra("Timer", 0);
                        newCourseVM.setTime(time);
                        // code to ensure if user stops moving, the code updates speed to 0.
                        if(!updated){
                            InitialDistance=0;
                        }
                        updated =false;

                        // make sure service is running to get data from service.
                        if (myService != null) {
                            Log.d("size", String.valueOf(newCourseVM.getLocations().size()));
                            newCourseVM.setLocations(myService.getMap());
//                            myService.setMap(newCourseVM.getLocations());
//                            Log.d("here","here");

                            // get data from service so that if the user backs out and then comes back, distance and location will not be lost.
                            newCourseVM.setDistance(myService.getDistance());
                            SetUpMap(newCourseVM.getLocations());
                        }
                        myService.getNotifications().notify(1, newCourseVM.setNotification(myService.getBuildNotification()));
                        newCourseVM.setSpeed(InitialDistance);
                        break;

                    case "Stop":
                        Log.d("Stop","Stop");
                        StopTracking();
                        break;
                }
            }

    };

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

}