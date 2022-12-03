package com.example.cw3.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cw3.entities.UserProfileEntities;

@Dao
public interface UserProfileDao {

    //Insert user name
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void UserName(UserProfileEntities UserName);

    //Insert user age
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void UserAge(UserProfileEntities UserAge);

    //Insert user name
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void UserWeight(UserProfileEntities UserWeight);

//    //Update user name
//    @Update
//    String UpdateUserName(String UserName);
//
//    //Update user name
//    @Update
//    int UpdateUserAge(int UserAge);
//
//    //Update user name
//    @Update
//    double UpdateUserWeight(double UserWeight);

    //Delete all courses
    @Query("DELETE FROM user_table")
    int deleteAll();
}
