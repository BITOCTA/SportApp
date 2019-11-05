package com.bitocta.sportapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.PlanRepository;
import com.bitocta.sportapp.TrainingRepository;
import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.entity.Training;

import java.util.List;

public class TrainingViewModel extends AndroidViewModel {
    private TrainingRepository mRepository;

    private LiveData<List<Training>> mAllTrainings;

    public TrainingViewModel (Application application) {
        super(application);
        mRepository = TrainingRepository.getInstance(AppDatabase.getDatabase(application));
        mAllTrainings = mRepository.getAllTrainings();
    }

    public LiveData<List<Training>> getAllProducts() { return mAllTrainings; }

    public void delete(Training training) { mRepository.delete(training);}

    public void insert(Training training) { mRepository.insert(training); }

    public void update(Training training) {mRepository.update(training);}
}
