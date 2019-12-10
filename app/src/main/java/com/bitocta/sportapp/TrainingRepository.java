package com.bitocta.sportapp;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.dao.ExerciseDao;
import com.bitocta.sportapp.db.dao.TrainingDao;
import com.bitocta.sportapp.db.entity.Exercise;
import com.bitocta.sportapp.db.entity.Training;
import com.bitocta.sportapp.db.entity.User;

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
        mTrainingDao.insert(training);
    }
    public void delete (Training training) { mTrainingDao.delete(training);}
}
