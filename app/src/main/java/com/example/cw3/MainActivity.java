package com.example.cw3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.cw3.databinding.ActivityMainBinding;

// Running logo resource taken from: https://www.iconsdb.com/black-icons/running-icon.html

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }
}