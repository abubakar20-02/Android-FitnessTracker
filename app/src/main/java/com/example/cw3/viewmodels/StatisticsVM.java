package com.example.cw3.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cw3.Database.CourseDB;
import com.example.cw3.entities.Course;

import java.util.ArrayList;
import java.util.List;

public class StatisticsVM extends ObservableVM{

    private final MutableLiveData<List<Course>> allCourses;
    private final LiveData<List<Course>> byDate;
    private final LiveData<List<Course>> byTime;
    private final LiveData<List<Course>> byDistance;
    private final LiveData<List<Course>> byAverageSpeed;
    private final LiveData<List<Course>> byRating;

    private List<Course> tempDate;
    private List<Course> tempTime;
    private List<Course> tempDistance;
    private List<Course> tempAverageSpeed;
    private List<Course> tempRating;


    public StatisticsVM(Application application){
        super(application);
        CourseDB courseRep = new CourseDB(application);

        tempDate = new ArrayList<>();
        tempTime = new ArrayList<>();
        tempDistance = new ArrayList<>();
        tempAverageSpeed = new ArrayList<>();
        tempRating = new ArrayList<>();

        allCourses = new MutableLiveData<>();
        allCourses.setValue(new ArrayList<>());

        byDate = courseRep.getCoursesByDate();
        byTime = courseRep.getCoursesByTime();
        byDistance = courseRep.getCoursesByDistance();
        byAverageSpeed = courseRep.getCoursesByAverageSpeed();
        byRating = courseRep.getCoursesByRating();
    }

    public void setByDate(List<Course> course, String choice) {
        tempDate = course;
        if (choice.equals("Date")) {
            setAllCourses(choice);
        }
    }
    public void setByTime(List<Course> course, String choice) {
        tempTime = course;
        if (choice.equals("Time")) {
            setAllCourses(choice);
        }
    }
    public void setByDistance(List<Course> course, String choice) {
        tempDistance = course;
        if (choice.equals("Distance")) {
            setAllCourses(choice);
        }
    }
    public void setByAverageSpeed(List<Course> course, String choice) {
        tempAverageSpeed = course;
        if (choice.equals("Average Speed")) {
            setAllCourses(choice);
        }
    }

    public void setByRating(List<Course> course, String choice) {
        tempRating = course;
        if (choice.equals("Rating")) {
            setAllCourses(choice);
        }
    }

    public MutableLiveData<List<Course>> getAllCourses() { return allCourses; }

    public void setAllCourses(String choice) {

        switch(choice) {
            case "Date":
                getAllCourses().setValue(tempDate);
                break;
            case "Distance":
                getAllCourses().setValue(tempDistance);
                break;
            case "Time":
                getAllCourses().setValue(tempTime);
                break;
            case "Average Speed":
                getAllCourses().setValue(tempAverageSpeed);
                break;
            case "Rating":
                getAllCourses().setValue(tempRating);
                break;
        }
    }

    public LiveData<List<Course>> getCoursesByDate() { return byDate; }

    public LiveData<List<Course>> getCoursesByTime() { return byTime; }

    public LiveData<List<Course>> getCoursesByDistance() { return byDistance; }

    public LiveData<List<Course>> getCoursesByAverageSpeed() { return byAverageSpeed; }

    public LiveData<List<Course>> getCoursesByRating() { return byRating; }

}
