package com.example.cw3;

import android.annotation.SuppressLint;

import java.util.Locale;

public class Tools {
    public String formatTime(int seconds){
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        return String.format(Locale.UK,"%02d:%02d:%02d", hours, minutes, seconds);
    }

    @SuppressLint("DefaultLocale")
    public String formatDistance(double meters){
        return (String.format("%.2f",(meters / 1000)) + " km");
    }

    @SuppressLint("DefaultLocale")
    public String formatAverageSpeed(double averageSpeed){
        return (String.format("%.2f",(averageSpeed)) + " km/hr");
    }
}
