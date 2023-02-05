package com.example.cw3.DAO;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cw3.entities.Course;

import java.util.List;

@Dao
public interface courseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCourse(Course course);

    @Update(entity = Course.class)
    int updateCourse(Course course);

    @Query("DELETE FROM course_table")
    int deleteAll();

    @Query("SELECT * FROM course_table ORDER BY courseID DESC")
    LiveData<List<Course>> sortByDate();

    @Query("SELECT * FROM course_table ORDER BY time DESC")
    LiveData<List<Course>> sortByTime();

    @Query("SELECT * FROM course_table ORDER BY rating DESC")
    LiveData<List<Course>> sortByRating();

    @Query("SELECT * FROM course_table ORDER BY distance DESC")
    LiveData<List<Course>> sortByDistance();

    @Query("SELECT * FROM course_table ORDER BY averageSpeed DESC")
    LiveData<List<Course>> sortByAverageSpeed();

    @Query("SELECT * FROM course_table WHERE courseID == :courseID")
    LiveData<Course> getCourse(int courseID);

    @Query("SELECT COUNT(*) FROM course_table")
    LiveData<Integer> getCount();

    @Query("SELECT * FROM course_table")
    Cursor getAllCoursesCursor();

    @Query("SELECT * FROM course_table WHERE courseID == :courseID")
    Cursor getCourseCursor(int courseID);

}
