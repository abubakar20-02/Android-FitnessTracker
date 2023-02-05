package com.example.cw3.Activities;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.cw3.Services.TrackingService;
import com.example.cw3.databinding.ActivityMainBinding;

import java.util.Objects;

//20314123
//hcyma5
//Muhammad Abubakar

// Running logo resource taken from: https://www.iconsdb.com/black-icons/running-icon.html

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.StartButton.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MainActivity.this, NewCourse.class);
                startActivity(intent);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
            }

        });


        //if permission not given, ask for permission.
        activityMainBinding.StatisticButton.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (checkServiceRunning(TrackingService.class)) {
                    Toast.makeText(getBaseContext(), "Stop running activity to access statistics", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, StatisticActivity.class);
                    startActivity(intent);
                }
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }
        });

        // removed button for profile system.

//        //if permission not given, ask for permission.
//        activityMainBinding.ProfileButton.setOnClickListener(v -> {
//            if (checkServiceRunning(TrackingService.class)){
//                Toast.makeText(getBaseContext(), "Stop running activity to access profile", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                Intent intent = new Intent(MainActivity.this, usersList.class);
//                startActivity(intent);
//            }
//        });

    }

    //https://stackoverflow.com/questions/600207/how-to-check-if-a-service-is-running-on-android
    /**
     * Check if the service is Running
     * @param serviceClass the class of the Service
     *
     * @return true if the service is running otherwise false
     */
    public boolean checkServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }
}