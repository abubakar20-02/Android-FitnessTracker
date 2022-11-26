//package com.example.cw3;
//
//import android.location.Location;
//import android.os.Bundle;
//import android.os.Looper;
//import android.util.Log;
//import android.view.LayoutInflater;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.Priority;
//
//public class Google extends AppCompatActivity {
//        private FusedLocationProviderClient fusedLocationClient;
//        private LocationCallback locationCallback;
//
//        protected void GetLocation() {
//                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//                LocationRequest locationRequest = new
//                        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build();
//                locationCallback = new LocationCallback() {
//                        @Override
//                        public void onLocationResult(@NonNull LocationResult locationResult) {
//                                for (Location location : locationResult.getLocations()) {
//                                        Log.d("comp3018", "location " + location.toString());
//                                }
//                        }
//                };
//                try {
//                        fusedLocationClient.requestLocationUpdates(locationRequest,
//                                locationCallback,
//                                Looper.getMainLooper());
//                } catch (SecurityException e) {
//                        // lacking permission to access location
//                }
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//                // ...
//                super.onCreate(savedInstanceState);
//                //Links the newCourse activity with it's viewmodel and creates binding object
//                model = new ViewModelProvider(this).get(NewCourseVM.class);
//                activityNewCourseBinding = ActivityNewCourseBinding.inflate(LayoutInflater.from(this));
//                setContentView(activityNewCourseBinding.getRoot());
//                activityNewCourseBinding.setViewmodel(model);
//
//                //Set Course ID and weight with the data from main activity
//                model.setCourseID(getIntent().getIntExtra("CourseID", 0));
//                model.setWeight(getIntent().getIntExtra("weight", 0));1
//                updateValuesFromBundle(savedInstanceState);
//        }
//
//        private void updateValuesFromBundle(Bundle savedInstanceState) {
//                if (savedInstanceState == null) {
//                        return;
//                }
//
//                // Update the value of requestingLocationUpdates from the Bundle.
//                if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
//                        requestingLocationUpdates = savedInstanceState.getBoolean(
//                                REQUESTING_LOCATION_UPDATES_KEY);
//                }
//
//                // ...
//
//                // Update UI to match restored state
//                updateUI();
//        }
//
//        @Override
//        protected void onResume() {
//                super.onResume();
//                if (requestingLocationUpdates) {
//                        startLocationUpdates();
//                }
//        }
//
//        private void startLocationUpdates() {
//                fusedLocationClient.requestLocationUpdates(locationRequest,
//                        locationCallback,
//                        Looper.getMainLooper());
//        }
//
//        @Override
//        protected void onPause() {
//                super.onPause();
//                stopLocationUpdates();
//        }
//
//        private void stopLocationUpdates() {
//                fusedLocationClient.removeLocationUpdates(locationCallback);
//        }
//
//        @Override
//        protected void onResume() {
//                super.onResume();
//                if (requestingLocationUpdates) {
//                        startLocationUpdates();
//                }
//        }
//
//        @Override
//        protected void onSaveInstanceState(Bundle outState) {
//                outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
//                        requestingLocationUpdates);
//                // ...
//                super.onSaveInstanceState(outState);
//        }
//
//
//
//}
//
