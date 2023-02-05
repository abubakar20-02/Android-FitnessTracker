package com.example.cw3.DAO;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cw3.entities.cords;

import java.util.List;

@Dao
public interface cordsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCords(List<cords> coursePoints);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCords(cords cords);

    @Query("DELETE FROM cords_table")
    int deleteAll();

    @Query("SELECT * FROM cords_table WHERE courseID == :courseID")
    LiveData<List<cords>> getCords(int courseID);

    @Query("SELECT * FROM cords_table")
    Cursor getAllCordsCursor();

    //Get cursor for one course
    @Query("SELECT * FROM cords_table WHERE courseID == :courseID")
    Cursor getCordsCursor(int courseID);

}
