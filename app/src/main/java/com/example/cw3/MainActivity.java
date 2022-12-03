package com.example.cw3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.cw3.databinding.ActivityMainBinding;

import java.util.Objects;

// Running logo resource taken from: https://www.iconsdb.com/black-icons/running-icon.html

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Links the main activity with it's viewmodel and creates binding object
        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(activityMainBinding.getRoot());

        //When the start button is pressed go to it's activity
        activityMainBinding.StartButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Course.class);
            startActivity(intent);
        });

        //When the start button is pressed go to it's activity
        activityMainBinding.StatisticButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatisticActivity.class);
            startActivity(intent);
        });

        //When the start button is pressed go to it's activity
        activityMainBinding.ProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, users.class);
            startActivity(intent);
        });

    }

    //When the activity is destroyed, remove all LiveData observers
    @Override
    protected void onDestroy() {
        Log.d("g53mdp", "MainActivity onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("g53mdp", "MainActivity onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("g53mdp", "MainActivity onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d("g53mdp", "MainActivity onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("g53mdp", "MainActivity onStop");
        super.onStop();
    }
}




