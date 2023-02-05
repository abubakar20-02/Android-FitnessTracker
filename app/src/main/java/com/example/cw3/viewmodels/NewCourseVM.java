package com.example.cw3.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cw3.BR;
import com.example.cw3.Database.CourseDB;
import com.example.cw3.Database.CordsDB;
import com.example.cw3.Tools;
import com.example.cw3.entities.Course;
import com.example.cw3.entities.cords;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewCourseVM extends ObservableVM {

    private Course course;
    private List<LatLng> locations;
    private int seconds;
    private final CordsDB CordsRep;
    private final CourseDB CourseRep;
    private Tools tools = new Tools();


    @Bindable
    private MutableLiveData<List<cords>> coursePoints;
    @Bindable
    private MutableLiveData<String> distance;
    @Bindable
    private MutableLiveData<String> time;
    @Bindable
    private MutableLiveData<String> speed;

    public NewCourseVM(@NonNull Application application) {
        super(application);
        CordsRep = new CordsDB(application);
        CourseRep = new CourseDB(application);
        course = new Course();
        locations = new ArrayList<>();
    }

    public LiveData<Integer> getSize(){
        return CourseRep.getSize();
    }


    public void setTime(int seconds) {
        this.seconds=seconds;
        course.setTime(seconds);
        getTime().setValue(tools.formatTime(seconds));
        notifyPropertyChanged(BR.time);
    }


    public MutableLiveData<String> getTime() {
        if (time == null) {
            time = new MutableLiveData<>();
            time.setValue("00:00:00");
        }
        return time;
    }

    public List<LatLng> getLocations() {
        return locations;
    }

    public void setLocations(List<LatLng> locations){
        this.locations = locations;
    }


    public MutableLiveData<String> getDistance() {
        if (distance == null) {
            distance = new MutableLiveData<>();
            distance.setValue("0.00 km");
        }
        return distance;
    }

    public void setDistance(double distance) {
        getDistance().setValue(tools.formatDistance(distance));
        course.setDistance(distance);
        notifyPropertyChanged(BR.distance);
    }

    public void saveCords(LatLng Cords) {
        cords coursePoint = new cords(course.getCourseID(), Cords.latitude, Cords.longitude);
        addCords(coursePoint);
        locations.add(Cords);
    }

    public void setSpeed(Double distance) {
        getSpeed().setValue(tools.formatAverageSpeed(distance*3.6));
        notifyPropertyChanged(BR.speed);
    }
    public MutableLiveData<String> getSpeed(){
        if (speed == null) {
            speed = new MutableLiveData<>();
            speed.setValue("0.00 km/hr");
        }
        return speed;
    }

    public MutableLiveData<List<cords>> getCords() {
        if (coursePoints == null) {
            coursePoints = new MutableLiveData<>();
            coursePoints.setValue(new ArrayList<>());
        }
        return coursePoints;
    }

    public void addCords(cords cords) {
        List<cords> allCords = getCords().getValue();
        assert allCords != null;
        allCords.add(cords);
        getCords().postValue(allCords);
    }



    //https://stackoverflow.com/questions/36257085/set-date-and-desired-time-in-android
    public void setDate(){
        Date date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            date = Calendar.getInstance().getTime();
        }
        DateFormat dateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        }
        String current = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            current = dateFormat.format(date);
        }
        course.setDate(current);
    }

    public void setCourseID(int courseID) {
        course.setCourseID(courseID);
    }

    //Set the notification with updated statistics
    public Notification setNotification(NotificationCompat.Builder builder) {
        builder.setSubText("Time: " + getTime().getValue());
        return builder.build();
    }


    @SuppressLint("Range")
    public void saveToDB() {
        course.setAverageSpeed((course.getDistance()/course.getTime())*3.6);
        if (getCords().getValue() != null && course.getDistance() != 0d) {

            CourseRep.insertCourse(course);
            CordsRep.insertCoursePoints(getCords().getValue());
            Toast.makeText(getApplication().getApplicationContext(), "Course saved", Toast.LENGTH_SHORT).show();
        }
    }

}