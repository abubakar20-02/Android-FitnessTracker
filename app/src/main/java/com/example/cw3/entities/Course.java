package com.example.cw3.entities;

import android.content.ContentValues;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey
    @ColumnInfo(name = "courseID")
    private int courseID;

    @ColumnInfo(name = "averageSpeed")
    private double averageSpeed;

    @ColumnInfo(name = "distance")
    private double distance;

    @ColumnInfo(name = "Date")
    private String Date;

    @ColumnInfo(name = "time")
    private int time;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "rating")
    private float rating;

    public Course() {
        this.distance = 0;
        this.Date = "";
        this.time = 0;
        this.rating=0;
        this.note="";
    }

    public int getCourseID() {
        return courseID;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public double getDistance() {
        return distance;
    }

    public String getDate() {
        return Date;
    }

    public int getTime() { return time; }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDate(String Date) { this.Date = Date; }

    public void setTime(int time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public static Course fromContentValues(@Nullable ContentValues values) {
        if (values != null && values.containsKey("courseName")) {
            Course course = new Course();

            if (values.containsKey("courseID")) {
                course.courseID = values.getAsInteger("courseID");
            }
            if (values.containsKey("averageSpeed")) {
                course.averageSpeed = values.getAsDouble("averageSpeed");
            }
            if (values.containsKey("distance")) {
                course.distance = values.getAsDouble("distance");
            }
            if (values.containsKey("Date")) {
                course.Date = values.getAsString("Date");
            }
            if (values.containsKey("time")) {
                course.time = values.getAsInteger("time");
            }
            if (values.containsKey("note")) {
                course.note = values.getAsString("note");
            }
            if (values.containsKey("rating")) {
                course.rating = values.getAsFloat("rating");
            }
            return course;
        }
        else {
            return null;
        }
    }
}