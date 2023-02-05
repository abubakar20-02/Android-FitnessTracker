package com.example.cw3.contentProviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.cw3.DAO.cordsDAO;
import com.example.cw3.DAO.courseDAO;
import com.example.cw3.Database.MyRoomDatabase;
import com.example.cw3.entities.Course;
import com.example.cw3.entities.cords;

public class Provider extends ContentProvider {

    private courseDAO courseDAO;
    private cordsDAO cordsDAO;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CourseProviderContract.AUTHORITY, "course_table", 1);
        uriMatcher.addURI(CourseProviderContract.AUTHORITY, "course_table/#", 2);
        uriMatcher.addURI(CourseProviderContract.AUTHORITY, "cords_table", 3);
        uriMatcher.addURI(CourseProviderContract.AUTHORITY, "cords_table/#", 4);
        uriMatcher.addURI(CourseProviderContract.AUTHORITY, "*", 5);
    }

    @Override
    public boolean onCreate() {
        MyRoomDatabase database = Room.databaseBuilder(this.getContext(), MyRoomDatabase.class, "runningDatabase").build();
        courseDAO = database.courseDao();
        cordsDAO = database.cordsDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        switch(uriMatcher.match(uri)) {
            case 1:
                return courseDAO.getCourseCursor(Integer.parseInt(uri.getLastPathSegment()));
            case 2:
                return courseDAO.getAllCoursesCursor();
            case 3:
                return cordsDAO.getCordsCursor(Integer.parseInt(uri.getLastPathSegment()));
            case 4:
                return cordsDAO.getAllCordsCursor();
            case 5:
                Cursor[] cursors = new Cursor[3];
                cursors[0] = courseDAO.getAllCoursesCursor();
                cursors[1] = cordsDAO.getAllCordsCursor();
                return new MergeCursor(cursors);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String contentType;

        if (uri.getLastPathSegment()==null) {
            contentType = CourseProviderContract.CONTENT_TYPE_MULTIPLE;
        } else {
            contentType = CourseProviderContract.CONTENT_TYPE_SINGLE;
        }

        return contentType;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id;
        Uri URI;
        switch (uriMatcher.match(uri)) {
            case 1:
                id = courseDAO.insertCourse(Course.fromContentValues(contentValues));
                URI = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(URI, null);
                return URI;
            case 3:
                id = cordsDAO.insertCords(cords.fromContentValues(contentValues));
                URI = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(URI, null);
                return URI;
            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int a;
        switch(uriMatcher.match(uri)) {
            case 2:
                a = courseDAO.deleteAll();
                getContext().getContentResolver().notifyChange(uri, null);
                return a;
            case 4:
                a = cordsDAO.deleteAll();
                getContext().getContentResolver().notifyChange(uri, null);
                return a;
            case 5:
                a = courseDAO.deleteAll() + cordsDAO.deleteAll();
                getContext().getContentResolver().notifyChange(uri, null);
                return a;
            default:
                throw new IllegalArgumentException("Invalid URI:" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        if (uriMatcher.match(uri) == 1 || uriMatcher.match(uri) == 2) {
            int a = courseDAO.updateCourse(Course.fromContentValues(contentValues));
            getContext().getContentResolver().notifyChange(uri, null);
            return a;
        }
        else {
            throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }
}
