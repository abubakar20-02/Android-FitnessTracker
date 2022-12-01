package com.example.cw3.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.example.cw3.entities.Course;

@Dao
public interface UserProfileDao {

    //Insert user name
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    String UserName(String UserName);

    //Insert user age
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    int UserAge(int UserAge);

    //Insert user name
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Double UserWeight(double UserWeight);

    //Update user name
    @Update
    String UpdateUserName(String UserName);

    //Update user name
    @Update
    int UpdateUserAge(int UserAge);

    //Update user name
    @Update
    double UpdateUserWeight(double UserWeight);
}
