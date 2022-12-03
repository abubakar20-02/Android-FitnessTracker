package com.example.cw3.DAO;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cw3.entities.Course;
import com.example.cw3.entities.UserProfileEntities;

import java.util.List;

@Dao
public interface UserProfileDao {

    //Insert user name
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void InsertUserProfile(UserProfileEntities UserProfile);


//    @Query("SELECT UserName FROM user_table")
//    LiveData<List<UserProfileEntities>> getUserName();

    //Delete all courses
    @Query("DELETE FROM user_table")
    int deleteAll();

    @Query("SELECT * FROM user_table")
    LiveData<List<UserProfileEntities>> SelectAll();

//
//    @Query("SELECT UserAge FROM user_table")
//    LiveData<Integer> getUserAge();
//
//    @Query("SELECT UserWeight FROM user_table")
//    LiveData<Double> getUserWeight();


//    //Insert user age
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void UserAge(UserProfileEntities UserAge);
//
//    //Insert user name
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void UserWeight(UserProfileEntities UserWeight);
//
//    //Get one course by course ID
//    @Query("SELECT UserName FROM user_table")
//    String getUserName();
//
//    @Query("SELECT UserAge FROM user_table")
//    int getUserAge();
//
//    @Query("SELECT UserWeight FROM user_table")
//    double getUserWeight();


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
}
