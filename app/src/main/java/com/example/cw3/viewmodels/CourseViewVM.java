package com.example.cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cw3.BR;
import com.example.cw3.Database.CordsDB;
import com.example.cw3.Database.CourseDB;
import com.example.cw3.Tools;
import com.example.cw3.entities.Course;
import com.example.cw3.entities.cords;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class CourseViewVM extends ObservableVM{
    private final CordsDB Cords;
    private final CourseDB Course;
    private List<LatLng> locations;
    private LiveData<Course> course;
    private int courseID;

    @Bindable
    private MutableLiveData<String> time;
    @Bindable
    private MutableLiveData<String> date;
    @Bindable
    private MutableLiveData<String> totalDistance;
    @Bindable
    private MutableLiveData<String> averageSpeed;

    Tools tools = new Tools();


    public CourseViewVM(@NonNull Application application) {
        super(application);
        Cords = new CordsDB(application);
        Course = new CourseDB(application);
    }

    public MutableLiveData<String> getTime() {
        if (time == null) {
            time = new MutableLiveData<>();
            time.setValue("00:00:00");
        }
        return time;
    }

    public MutableLiveData<String> getDate() {
        if (date == null) {
            date = new MutableLiveData<>();
            date.setValue("None");
        }
        return date;
    }

    public MutableLiveData<String> getTotalDistance() {
        if (totalDistance == null) {
            totalDistance = new MutableLiveData<>();
            totalDistance.setValue("0.00 km");
        }
        return totalDistance;
    }

    public MutableLiveData<String> getAverageSpeed() {
        if (averageSpeed == null) {
            averageSpeed = new MutableLiveData<>();
            averageSpeed.setValue("0.00 km/hr");
        }
        return averageSpeed;
    }

    public void setLocations(List<cords> Cords) {
        locations = new ArrayList<>();
        for (int i = 0; i < Cords.size(); i++) {
            LatLng latLng = new LatLng(Cords.get(i).getLatitude(), Cords.get(i).getLongitude());
            locations.add(latLng);
        }
    }

    public List<LatLng> getLocations() {
        return locations;
    }

    public LiveData<List<cords>> getCords() {
        return Cords.getCoursePoints(courseID);
    }

    public LiveData<Course> getCourse() {
        course = Course.getCourse(courseID);
        return course;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    //Save name/ rating change and update repository
    public void setRatingAndNote(float rating, String note) {
        course.getValue().setRating(rating);
        course.getValue().setNote(note);
        Course.update(course.getValue());
    }

    public void setVariables(Course course) {
        getDate().setValue(course.getDate());
        getTotalDistance().setValue(tools.formatDistance(course.getDistance()));
        getTime().setValue(tools.formatTime(course.getTime()));
        getAverageSpeed().setValue(tools.formatAverageSpeed(course.getAverageSpeed()));

        notifyPropertyChanged(BR.date);
        notifyPropertyChanged(BR.totalDistance);
        notifyPropertyChanged(BR.time);
        notifyPropertyChanged(BR.averageSpeed);
    }
}
