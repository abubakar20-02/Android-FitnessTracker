package com.example.cw3.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cw3.entities.UserProfileEntities;

import java.util.List;

@Dao
public interface UserProfileDao {

    //Insert user name
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void InsertUserProfile(UserProfileEntities UserProfile);


    @Query("DELETE FROM user_table")
    int deleteAll();

    @Query("SELECT * FROM user_table")
    LiveData<List<UserProfileEntities>> SelectAll();

    @Query("DELETE FROM user_table WHERE UserName== :userName")
    void deleteProfile(String userName);

    //Update one course
    @Query("UPDATE user_table SET UserSelected = :userSelected")
    void updateUserProfiles(boolean userSelected);

    @Query("UPDATE user_table SET UserSelected = :userSelected WHERE UserName == :userName")
    void updateUserProfile(String userName, boolean userSelected);

    @Query("SELECT userName FROM user_table WHERE UserSelected== :userSelected")
    LiveData<String> getSelectedUser(boolean userSelected);

}
