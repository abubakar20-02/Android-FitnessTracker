package com.example.cw3.contentProviders;

import android.net.Uri;

public class CourseProviderContract {
    //Declare constant variables that describe the nature of the Uris it accepts and declare table column names
    public static final String AUTHORITY = "com.example.cw3.contentProviders.Provider";

    public static final Uri COURSE_URI = Uri.parse("content://"+AUTHORITY+"/course_table");
    public static final Uri CORDS_URI = Uri.parse("content://"+AUTHORITY+"/cords_table");
    public static final Uri ALL_URI = Uri.parse("content://"+AUTHORITY+"/");

    public static final String COURSEID = "courseID";
    public static final String AVERAGESPEED = "averageSpeed";
    public static final String DISTANCE = "distance";
    public static final String DATE = "Date";
    public static final String TIME = "time";
    public static final String NOTE = "note";
    public static final String RATING = "rating";


    public static final String CORDID = "courseID";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/CourseProvider.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/CourseProvider.data.text";
}
