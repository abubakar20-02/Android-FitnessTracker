package com.example.cw3.entities;

import android.content.ContentValues;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cords_table")
public class cords {

    @ColumnInfo(name = "courseID")
    private int courseID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cordID")
    private int cordID;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    public cords(int courseID, double latitude, double longitude) {
        this.courseID = courseID;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCordID() {
        return cordID;
    }

    public void setCordID(int cordID) {
        this.cordID = cordID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static cords fromContentValues(@Nullable ContentValues values) {
        if (values != null && values.containsKey("courseID") && values.containsKey("latitude") && values.containsKey("longitude")) {
            cords cords = new cords(values.getAsInteger("courseID"), values.getAsDouble("latitude"), values.getAsDouble("longitude"));
            return cords;
        }
        else {
            return null;
        }
    }
}
