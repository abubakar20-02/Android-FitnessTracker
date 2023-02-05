package com.example.cw3.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cw3.entities.users;
import java.util.List;

@Dao
public interface userDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long InsertUserProfile(users UserProfile);

    @Query("DELETE FROM user_table")
    int deleteAll();

    @Query("SELECT * FROM user_table")
    LiveData<List<users>> SelectAll();

    @Query("DELETE FROM user_table WHERE UserName== :userName")
    void deleteProfile(String userName);

    @Query("UPDATE user_table SET UserSelected = :userSelected")
    void updateUserProfiles(boolean userSelected);

    @Query("UPDATE user_table SET UserSelected = :userSelected WHERE UserName == :userName")
    void updateUserProfile(String userName, boolean userSelected);

    @Query("SELECT userName FROM user_table WHERE UserSelected== :userSelected")
    LiveData<String> getSelectedUser(boolean userSelected);

}
