//package com.example.cw3.Database;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//import com.example.cw3.DAO.Dao1;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Database(entities = {Class.class}, version = 5, exportSchema = false)
//public abstract class MyRoomDatabase extends RoomDatabase {
//
//    private static volatile MyRoomDatabase INSTANCE;
//    private static final int NUMBER_OF_THREADS = 4;
//    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
//
//    //Declare dao objects
//    public abstract Dao1 Dao1();
//
//
////    //Database getter
////    public static MyRoomDatabase getDatabase(final Context context) {
////        if (INSTANCE == null) {
////            synchronized (MyRoomDatabase.class) {
////                if (INSTANCE == null) {
////                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
////                                    MyRoomDatabase.class, "runningDatabase")
////                            .fallbackToDestructiveMigration()
////                            .addCallback(createCallback)
////                            .build();
////                }
////            }
////        }
////        return INSTANCE;
////    }
//
////    private static final RoomDatabase.Callback createCallback = new RoomDatabase.Callback() {
////        @Override
////        public void onCreate(@NonNull SupportSQLiteDatabase db) {
////            super.onCreate(db);
////
////            Log.d("g53mdp", "dboncreate");
////            databaseWriteExecutor.execute(() -> {
////                // Populate the database in the background.
////                // If you want to start with more words, just add them.
////                FruitDao fruitDao = instance.fruitDao();
////                fruitDao.deleteAll();
////
////                Fruit fruit = new Fruit("mango", "orange");
////                fruitDao.insert(fruit);
////
////                AnimalDao animalDao = instance.animalDao();
////                animalDao.deleteAll();
////
////                Animal animal = new Animal("scribble", "cat");
////                animalDao.insert(animal);
////
////                animal = new Animal("hector", "dog");
////                animalDao.insert(animal);
////            });
////        }
////    }
//}
