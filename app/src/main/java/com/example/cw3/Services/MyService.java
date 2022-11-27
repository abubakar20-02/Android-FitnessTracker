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

import com.example.cw3.Course;
import com.example.cw3.R;

import java.util.Timer;
import java.util.TimerTask;

//Service to track location, time and notifications
public class MyService extends Service {

    //Declare objects and tick count
    private NotificationManager notifications;
    private NotificationCompat.Builder buildNotification;

    //Getter for notification manager
    public NotificationManager getNotifications() {
        return notifications;
    }

    //Getter for notification builder
    public NotificationCompat.Builder getBuildNotification() {
        return buildNotification;
    }

    //Binder class allowing other applications to bind and create connections
    public class MyBinder extends Binder implements IInterface {

        //Defines interface that specifies how a client can communicate with the service
        @Override
        public IBinder asBinder() {
            return this;
        }

        //Getters for notification objects
        public NotificationManager getNotifications() {
            return MyService.this.getNotifications();
        }
        public NotificationCompat.Builder getBuildNotification() { return MyService.this.getBuildNotification(); }
    }

    //Initialise notifications
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createNotifications() {

        //Create new notification manager and cancel any notifications in the same ID slot
        notifications = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifications.cancel(1);
        String CHANNEL_ID = "100";
        CharSequence name = "Running App";
        //Create new notification channel with high importance so notification stays at the top of the list
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
        }
        //add channel to the notification manager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notifications.createNotificationChannel(channel);
        }
        //Create new intent between new course activity and this service
        Intent intent = new Intent(this, Course.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //Create pending intent with selected intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        //Build notification, setting the notification to silent and making it top priority
        //Also adding so whenever the notification is pressed it will return to the activity
        buildNotification = new NotificationCompat.Builder(this, CHANNEL_ID).setNotificationSilent().setWhen(0)
                .setPriority(NotificationCompat.PRIORITY_MAX).setContentIntent(pendingIntent);
    }

    //Creates pending button intents for the notification bar
    @RequiresApi(api = Build.VERSION_CODES.M)
    public PendingIntent buttonIntents(String name) {
        Intent intent = new Intent(name);
        return PendingIntent.getBroadcast(MyService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    //Update notification buttons for pausing, resuming and finishing
    //Add custom notification logo then notify the notification manager of the changes
    public void startNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            buildNotification.setContentTitle("Running App")
                    .setSmallIcon(R.drawable.running)
                    .addAction(R.drawable.ic_launcher_background, "Start", buttonIntents("Start"))
                    .addAction(R.drawable.ic_launcher_background, "Finish", buttonIntents("Finish"))
                    .addAction(R.drawable.ic_launcher_background, "Pause", buttonIntents("Pause"));
        }
        notifications.notify(1, buildNotification.build());
    }

    //On creation of the service instantiate the MyService class and create the notification manager, and start the timer/GPS
    @Override
    public void onCreate() {
        Log.d("g53mdp", "service onCreate");
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createNotifications();
        }
        startNotifications();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.d("g53mdp", "service onBind");
        return new MyBinder();
    }

    //When the foreground service is started then declare the notification as foregrounded
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("g53mdp", "service onStartCommand");
        startForeground(1, buildNotification.build());
        return Service.START_STICKY;
    }

    //When service ends then reset the threads and cancel all notifications open, stop the foreground service
    @Override
    public void onDestroy() {
        Log.d("g53mdp", "service onDestroy");
//        stopForeground(true);
        notifications.cancelAll();
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("g53mdp", "service onRebind");
        super.onRebind(intent);
    }

    //When the task is prematurely ended stop the service
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("g53mdp", "service onTaskRemoved");
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("g53mdp", "service onUnbind");
        return true;
    }
}