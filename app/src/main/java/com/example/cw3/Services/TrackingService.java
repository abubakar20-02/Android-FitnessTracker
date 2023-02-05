package com.example.cw3.Services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.cw3.Activities.NewCourse;
import com.example.cw3.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TrackingService extends Service {
    private LocationManager locationManager;
    private MyLocationListener listener;
    private Timer time = null;
    private int tick = 0;
    private double distance = 0.0;
    private List<LatLng> cords = new ArrayList<>();
    private NotificationManager notifications;
    private NotificationCompat.Builder buildNotification;

    public class MyBinder extends Binder implements IInterface {
        @Override
        public IBinder asBinder() {
            return this;
        }
        public NotificationManager getNotifications() { return TrackingService.this.getNotifications(); }
        public NotificationCompat.Builder getBuildNotification() { return TrackingService.this.getBuildNotification(); }
        public void setDistance(double distance) { TrackingService.this.setDistance(distance);}
        public double getDistance() {return TrackingService.this.getDistance();}
        public void setMap(List<LatLng> latLngs) { TrackingService.this.setUpMap(latLngs);}
        public List<LatLng> getMap() {return TrackingService.this.getMap();}
    }


    private NotificationManager getNotifications() {
        return notifications;
    }

    private NotificationCompat.Builder getBuildNotification() {
        return buildNotification;
    }

    private List<LatLng> getMap() {
        return cords;
    }

    private void setUpMap(List<LatLng> latLngs) {
        this.cords = latLngs;
    }

    private void setDistance(double distance) {
        this.distance= distance;
    }

    public double getDistance(){
        return distance;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createNotifications() {

        notifications = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifications.cancel(1);
        String CHANNEL_ID = "1";
        CharSequence name = "Fitness app";

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notifications.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, NewCourse.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        buildNotification = new NotificationCompat.Builder(this, CHANNEL_ID).setNotificationSilent().setWhen(0)
                .setPriority(NotificationCompat.PRIORITY_MAX).setContentIntent(pendingIntent);
    }

    public void startNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            buildNotification
                    .addAction(R.drawable.ic_launcher_background, "Stop", PendingIntent.getBroadcast(TrackingService.this, 0, new Intent("Stop"), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE))
                    .setSmallIcon(R.drawable.running);
        }
        notifications.notify(1, buildNotification.build());
    }

    //Timer thread which updates timer every second, sending a broadcast to be received
    public void startTime() {

        time = new Timer();
        TimerTask myTask = new TimerTask() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                ++tick;
                Intent intentTime = new Intent("Time");
                intentTime.putExtra("Timer", tick);
                sendBroadcast(intentTime);
            }
        };
        time.schedule(myTask, 1000, 1000);
    }

    //Location tracker which requests user location at every update, only if permissions enabled
    public void startGPS() {
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            listener = new MyLocationListener();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, listener);
    }

    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(final Location loc) {
            Intent intentLocation = new Intent("Location");
            intentLocation.putExtra("Latitude", loc.getLatitude());
            intentLocation.putExtra("Longitude", loc.getLongitude());
            sendBroadcast(intentLocation);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("g53mdp", "onStatusChanged: " + provider + " " + status);
        }
        @Override
        public void onProviderEnabled(String provider) {
            Log.d("g53mdp", "onProviderEnabled: " + provider);
        }
        @Override
        public void onProviderDisabled(String provider) {
            Log.d("g53mdp", "onProviderDisabled: " + provider);
        }
    }

    @Override
    public void onCreate() {
        Log.d("g53mdp", "service onCreate");
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createNotifications();
        }
        startNotification();
        startGPS();
        startTime();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("g53mdp", "service onStartCommand");
        startForeground(1, buildNotification.build());
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("g53mdp", "service onDestroy");
        locationManager.removeUpdates(listener);
        locationManager = null;
        listener = null;
        time.cancel();
        stopForeground(true);
        notifications.cancelAll();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.d("g53mdp", "service onBind");
        return new MyBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("g53mdp", "service onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("g53mdp", "service onTaskRemoved");
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

}