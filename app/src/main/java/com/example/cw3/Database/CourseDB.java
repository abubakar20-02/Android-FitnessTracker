package com.example.cw3.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cw3.DAO.courseDAO;

import java.util.List;
import com.example.cw3.entities.Course;

public class CourseDB {

    private final courseDAO courseDAO;

    public CourseDB(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        courseDAO = db.courseDao();

    }

    public LiveData<List<Course>> getCoursesByDate() { return courseDAO.sortByDate(); }

    public LiveData<List<Course>> getCoursesByTime() { return courseDAO.sortByTime(); }

    public LiveData<List<Course>> getCoursesByDistance() { return courseDAO.sortByDistance(); }

    public LiveData<List<Course>> getCoursesByAverageSpeed() { return courseDAO.sortByAverageSpeed(); }

    public LiveData<List<Course>> getCoursesByRating() { return courseDAO.sortByRating(); }

    public LiveData<Course> getCourse(int courseID) { return courseDAO.getCourse(courseID); }

    public void insertCourse(Course course) {MyRoomDatabase.databaseWriteExecutor.execute(() -> courseDAO.insertCourse(course));}

    public LiveData<Integer> getSize() {
        return courseDAO.getCount();
    }

    public void update(Course course) {MyRoomDatabase.databaseWriteExecutor.execute(() -> courseDAO.updateCourse(course));}

}