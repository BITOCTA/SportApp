package com.bitocta.sportapp;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.dao.ExerciseDao;

import com.bitocta.sportapp.db.entity.Exercise;


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
        new ExerciseRepository.insertAsyncTask(mExerciseDao).execute(exercise);
    }
    public void delete (Exercise exercise) { new ExerciseRepository.deleteAsyncTask(mExerciseDao).execute(exercise);}
    public void update (Exercise exercise) {new ExerciseRepository.updateAsyncTask(mExerciseDao).execute(exercise);}

    private static class insertAsyncTask extends AsyncTask<Exercise, Void, Void> {

        private ExerciseDao mAsyncTaskDao;

        insertAsyncTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Exercise... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Exercise, Void, Void>{
        private ExerciseDao mAsyncTaskDao;

        deleteAsyncTask(ExerciseDao dao) {mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Exercise... params){
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao mAsyncTaskDao;

        updateAsyncTask(ExerciseDao dao) { mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Exercise... params){
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
