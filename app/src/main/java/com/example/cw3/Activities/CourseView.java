package com.example.cw3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.cw3.R;
import com.example.cw3.databinding.ActivityCourseViewBinding;
import com.example.cw3.viewmodels.CourseViewVM;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Objects;

public class CourseView extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CourseViewVM courseViewVM;
    private ActivityCourseViewBinding activityCourseViewBinding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();

        courseViewVM = new ViewModelProvider(this).get(CourseViewVM.class);


        activityCourseViewBinding = ActivityCourseViewBinding.inflate(LayoutInflater.from(this));
        activityCourseViewBinding.setViewmodel(courseViewVM);
        setContentView(activityCourseViewBinding.getRoot());

        int CourseID = getIntent().getExtras().getInt("CourseID");
        courseViewVM.setCourseID(CourseID);
        courseViewVM.getCourse().observe(this, course -> {
            if (course != null) {
                courseViewVM.setVariables(course);
                activityCourseViewBinding.ratingBar.setRating(course.getRating());
                activityCourseViewBinding.Note.setText(course.getNote());
            }

        });

        courseViewVM.getCords().observe(this, CoursePoint -> {
            courseViewVM.setLocations(CoursePoint);
            setUpMap(courseViewVM.getLocations());
        });
        setUpMap(courseViewVM.getLocations());


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }

    // draw map with user movement plotted.
    private void setUpMap(List<LatLng> cords) {
        if (mMap != null) {
            mMap.clear();

            if (!cords.isEmpty()) {
                PolylineOptions options = new PolylineOptions().color(Color.GREEN).width(5).addAll(cords);
                mMap.addPolyline(options);
                mMap.addMarker(new MarkerOptions().position(cords.get(0)).title("Start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(cords.get(cords.size()-1)).title("Finish")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cords.get((cords.size() - 1)/2), 15));
            }
        }
    }

    @Override
    protected void onStop() {
        courseViewVM.setRatingAndNote(activityCourseViewBinding.ratingBar.getRating(),activityCourseViewBinding.Note.getText().toString());
        super.onStop();
    }
}