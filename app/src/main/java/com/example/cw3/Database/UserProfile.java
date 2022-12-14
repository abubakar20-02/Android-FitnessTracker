package com.example.cw3.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cw3.DAO.UserProfileDao;
import com.example.cw3.entities.UserProfileEntities;

import java.util.List;

public class UserProfile {

    //Declare dao objects
    private final UserProfileDao userProfileDao;

    //Initialise dao objects and link database
    public UserProfile(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        userProfileDao = db.userProfileDao();
    }

    public LiveData<List<UserProfileEntities>> getAllUsers() { return userProfileDao.SelectAll(); }

//    public LiveData<UserProfileEntities> getSelectedUser() {return userProfileDao.}

    //Create functions which link dao queries to the viewmodels
//    public LiveData<String> getUserName() { return userProfileDao.getUserName(); }

//    public LiveData<Integer> getUserAge() { return userProfileDao.getUserAge(); }
//
//    public LiveData<Double> getUserWeight() { return userProfileDao.getUserWeight(); }


    //Create functions which link dao queries to the viewmodels
//    public MutableLiveData<String> getUserName() { return userProfileDao.getUserName(); }
//
//    public MutableLiveData<String> getUserAge() { return userProfileDao.getUserAge(); }
//
//    public MutableLiveData<String> getUserWeight() { return userProfileDao.getUserWeight(); }

    public void insertUserProfile(UserProfileEntities UserProfile) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userProfileDao.InsertUserProfile(UserProfile));
    }

    public void deleteUserProfile(String userName){
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userProfileDao.deleteProfile(userName));
    }

    public void update(boolean a) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userProfileDao.updateUserProfiles(a));
    }

    public void selectProfile(String userName) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userProfileDao.updateUserProfile(userName,true));
    }

    public LiveData<String> getSelectedUser() {
        return userProfileDao.getSelectedUser(true);
    }

//    public void deleteUserProfile(boolean UserSelected){
//        MyRoomDatabase.databaseWriteExecutor.execute(() -> userProfileDao.(UserSelected));
//    }
}