package com.example.cw3.Database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.cw3.DAO.userDAO;
import com.example.cw3.entities.users;
import java.util.List;

public class UserDB {

    private final userDAO userDAO;

    public UserDB(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        userDAO = db.userProfileDao();
    }

    public LiveData<List<users>> getAllUsers() { return userDAO.SelectAll(); }

    public void insertUserProfile(users UserProfile) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userDAO.InsertUserProfile(UserProfile));
    }

    public void deleteUserProfile(String userName){
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userDAO.deleteProfile(userName));
    }

    public void setAllUserSelectedTo(boolean selected) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userDAO.updateUserProfiles(selected));
    }

    public void selectProfile(String userName) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> userDAO.updateUserProfile(userName,true));
    }

    public LiveData<String> getSelectedUser() {
        return userDAO.getSelectedUser(true);
    }

}