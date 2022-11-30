package com.example.cw3.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cw3.DAO.CourseDao;
import com.example.cw3.entities.Course;
import com.example.cw3.entities.CoursePoint;

import java.util.List;

public class MyRepository {

    //Declare dao objects
    private final CourseDao courseDao;

    //Initialise dao objects and link database
    public MyRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        courseDao = db.courseDao();
    }

    //Create functions which link dao queries to the viewmodels
    public LiveData<List<Course>> getCoursesByDate() { return courseDao.getCoursesByDate(); }

    public LiveData<List<Course>> getCoursesByTime() { return courseDao.getCoursesByTime(); }

    public LiveData<List<Course>> getCoursesByDistance() { return courseDao.getCoursesByDistance(); }

    public LiveData<List<Course>> getCoursesByPace() { return courseDao.getCoursesByPace(); }

    public LiveData<List<Course>> getCoursesByCalories() { return courseDao.getCoursesByCalories(); }

    public LiveData<List<Course>> getCoursesByWeight() { return courseDao.getCoursesByWeight(); }

    public LiveData<List<Course>> getCoursesByRating() { return courseDao.getCoursesByRating(); }

    public LiveData<Course> getCourse(int courseID) { return courseDao.getCourse(courseID); }

    public void insertCourse(Course course) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> courseDao.insertCourse(course));
    }

    public void update(Course course) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> courseDao.updateCourse(course));
    }

    public void deleteCourse(Course course) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> courseDao.deleteCourse(course.getCourseID()));
    }
}