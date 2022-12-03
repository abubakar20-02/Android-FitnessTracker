package com.example.cw3.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cw3.DAO.CourseDao;
import com.example.cw3.DAO.UserProfileDao;
import com.example.cw3.entities.Course;
import com.example.cw3.entities.CoursePoint;

import java.util.List;

public class UserProfile {

    //Declare dao objects
    private final UserProfileDao userProfileDao;

    //Initialise dao objects and link database
    public UserProfile(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        userProfileDao = db.userProfileDao();
    }

//    public void insertUserName(String UserName) {
//        MyRoomDatabase.databaseWriteExecutor.execute(() -> userProfileDao.UserName(UserName));
//    }

}