package com.example.cw3.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cw3.DAO.courseDAO;
import com.example.cw3.DAO.cordsDAO;
import com.example.cw3.DAO.userDAO;
import com.example.cw3.entities.Course;
import com.example.cw3.entities.cords;
import com.example.cw3.entities.users;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Course.class, users.class, cords.class}, version = 11, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract courseDAO courseDao();
    public abstract userDAO userProfileDao();
    public abstract cordsDAO cordsDao();

    private static volatile MyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 6;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDatabase.class, "Database")
                            .fallbackToDestructiveMigration()
                            .addCallback(createCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback createCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.d("g53mdp", "Database Created");
            databaseWriteExecutor.execute(() -> {

                courseDAO courseDao = INSTANCE.courseDao();
                courseDao.deleteAll();

                userDAO userDAO = INSTANCE.userProfileDao();
                userDAO.deleteAll();

                cordsDAO cordsDao = INSTANCE.cordsDao();
                cordsDao.deleteAll();
            });
        }
    };
}
