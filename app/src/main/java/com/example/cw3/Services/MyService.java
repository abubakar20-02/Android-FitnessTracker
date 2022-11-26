//package com.example.cw3.Services;
//
////import android.location.LocationListener;
////import android.location.LocationManager;
//
//import android.app.NotificationManager;
//import android.app.Service;
//import android.location.LocationManager;
//import android.os.Binder;
//import android.os.IBinder;
//import android.os.IInterface;
//
//import androidx.core.app.NotificationCompat;
//
//import java.util.Timer;
//
//public class MyService extends Service {
//
//    //Declare objects and tick count
//    private LocationManager locationManager;
////    private MyLocationListener listener;
//    private NotificationManager notifications;
//    private NotificationCompat.Builder buildNotification;
//    private Timer timer = null;
//    private int tickCount = 0;
//
//    //Getter for notification manager
//    public NotificationManager getNotifications() {
//        return notifications;
//    }
//
//    //Getter for notification builder
//    public NotificationCompat.Builder getBuildNotification() {
//        return buildNotification;
//    }
//
//    //Binder class allowing other applications to bind and create connections
//    public class MyBinder extends Binder implements IInterface {
//
//        //Defines interface that specifies how a client can communicate with the service
//        @Override
//        public IBinder asBinder() {
//            return this;
//        }
//
//        //Pause and start functions for timer and gps
//        public void pauseGPS() {
//            MyService.this.pauseGPS();
//        }
//        public void pauseTimer() {
//            MyService.this.pauseTimer();
//        }
//        public void startGPS() {
//            MyService.this.startGPS();
//        }
//        public void startTimer() {
//            MyService.this.startTimer();
//        }
//        //Getters for notification objects
//        public NotificationManager getNotifications() {
//            return MyService.this.getNotifications();
//        }
//        public NotificationCompat.Builder getBuildNotification() { return MyService.this.getBuildNotification(); }
//    }
//
//}
