package com.example.cw3.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Declare table name
@Entity(tableName = "CourseTable")
public class CourseStatistics {

    //Declare table elements
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CourseID")
    private int courseID;

    @ColumnInfo(name = "Date")
    private String Date;



    @ColumnInfo(name = "Time")
    private String Time;

    @ColumnInfo(name = "Distance")
    private double Distance;

    @ColumnInfo(name = "Speed")
    private double Speed;

    @ColumnInfo(name = "Calories")
    private double Calories;



    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public double getCalories() {
        return Calories;
    }

    public void setCalories(double calories) {
        Calories = calories;
    }

    public double getSpeed() {
        return Speed;
    }

    public void setSpeed(double speed) {
        Speed = speed;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

}
