package com.bitocta.sportapp;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.dao.ExerciseDao;

import com.bitocta.sportapp.db.entity.Exercise;
import com.bitocta.sportapp.db.entity.User;


import java.util.List;

public class ExerciseRepository {


    private static ExerciseRepository instance;
    private final AppDatabase database;

    private ExerciseDao mExerciseDao;
    private LiveData<List<Exercise>> mAllExercises;

    ExerciseRepository(final AppDatabase database){
        this.database=database;
        mExerciseDao=this.database.exerciseDao();
        mAllExercises = mExerciseDao.getAll();
    }

    public static ExerciseRepository getInstance(final AppDatabase database) {
        if (instance == null) {
            synchronized (ExerciseRepository.class) {
                if (instance == null) {
                    instance = new ExerciseRepository(database);
                }
            }
        }
        return instance;
    }



    public LiveData<List<Exercise>> getAllExercises() {
        return mAllExercises;
    }

    public void insert (Exercise exercise) {
        mExerciseDao.insert(exercise);
    }
    public void delete (Exercise exercise) { mExerciseDao.delete(exercise);}
}
