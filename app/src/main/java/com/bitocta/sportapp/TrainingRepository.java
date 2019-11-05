package com.bitocta.sportapp;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.dao.ExerciseDao;
import com.bitocta.sportapp.db.dao.TrainingDao;
import com.bitocta.sportapp.db.entity.Exercise;
import com.bitocta.sportapp.db.entity.Training;

import java.util.List;

public class TrainingRepository {

    private static TrainingRepository instance;
    private final AppDatabase database;

    private TrainingDao mTrainingDao;
    private LiveData<List<Training>> mAllTrainings;

    TrainingRepository(final AppDatabase database){
        this.database=database;
        mTrainingDao=this.database.trainingDao();
        mAllTrainings = mTrainingDao.getAll();
    }

    public static TrainingRepository getInstance(final AppDatabase database) {
        if (instance == null) {
            synchronized (TrainingRepository.class) {
                if (instance == null) {
                    instance = new TrainingRepository(database);
                }
            }
        }
        return instance;
    }



    public LiveData<List<Training>> getAllTrainings() {
        return mAllTrainings;
    }

    public void insert (Training training) {
        new TrainingRepository.insertAsyncTask(mTrainingDao).execute(training);
    }
    public void delete (Training training) { new TrainingRepository.deleteAsyncTask(mTrainingDao).execute(training);}
    public void update (Training training) {new TrainingRepository.updateAsyncTask(mTrainingDao).execute(training);}

    private static class insertAsyncTask extends AsyncTask<Training, Void, Void> {

        private TrainingDao mAsyncTaskDao;

        insertAsyncTask(TrainingDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Training... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Training, Void, Void>{
        private TrainingDao mAsyncTaskDao;

        deleteAsyncTask(TrainingDao dao) {mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Training... params){
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Training, Void, Void> {
        private TrainingDao mAsyncTaskDao;

        updateAsyncTask(TrainingDao dao) { mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Training... params){
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
