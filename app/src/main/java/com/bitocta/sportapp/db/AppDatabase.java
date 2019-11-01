package com.bitocta.sportapp.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bitocta.sportapp.db.entity.Exercise;
import com.bitocta.sportapp.db.entity.Plan;
import com.bitocta.sportapp.db.entity.Training;
import com.bitocta.sportapp.db.entity.User;

@Database(entities = {Exercise.class, User.class, Plan.class, Training.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public static final String DATABASE_NAME = "database";

    public static AppDatabase getDatabase(final Context context) {

        instance = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback)
                .build();

        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };


}