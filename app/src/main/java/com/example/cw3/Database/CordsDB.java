package com.example.cw3.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cw3.DAO.cordsDAO;
import com.example.cw3.entities.cords;

import java.util.List;

public class CordsDB {

    private final cordsDAO cordsDAO;

    public CordsDB(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        cordsDAO = db.cordsDao();
    }

    public LiveData<List<cords>> getCoursePoints(int courseID) { return cordsDAO.getCords(courseID); }

    public void insertCoursePoints(List<cords> coursePoint) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> cordsDAO.insertCords(coursePoint));
    }
}