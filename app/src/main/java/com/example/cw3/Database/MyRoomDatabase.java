package com.example.cw3.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cw3.DAO.CourseDao;
import com.example.cw3.DAO.UserProfileDao;
import com.example.cw3.entities.Course;
import com.example.cw3.entities.UserProfileEntities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Declare database entities
@Database(entities = {Course.class, UserProfileEntities.class}, version = 11, exportSchema = false) // drop and recreate
public abstract class MyRoomDatabase extends RoomDatabase {

    //Declare dao objects
    public abstract CourseDao courseDao();
    public abstract UserProfileDao userProfileDao();

    private static volatile MyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Database getter
    static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDatabase.class, "runningDatabase")
                            .fallbackToDestructiveMigration()
                            .addCallback(createCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //When database created make sure all tables clear
    private static final RoomDatabase.Callback createCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Log.d("g53mdp", "Database Created");
            databaseWriteExecutor.execute(() -> {

                CourseDao courseDao = INSTANCE.courseDao();
                courseDao.deleteAll();

                UserProfileDao userProfileDao = INSTANCE.userProfileDao();
                userProfileDao.deleteAll();
            });
        }
    };
}
