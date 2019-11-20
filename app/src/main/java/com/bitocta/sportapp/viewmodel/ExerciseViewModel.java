package com.bitocta.sportapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.bitocta.sportapp.ExerciseRepository;
import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.entity.Exercise;


import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository mRepository;

    private LiveData<List<Exercise>> mAllExercises;

    public ExerciseViewModel (Application application) {
        super(application);
        mRepository = ExerciseRepository.getInstance(AppDatabase.getDatabase(application));
        mAllExercises = mRepository.getAllExercises();
    }

    public LiveData<List<Exercise>> getAllExercises() { return mAllExercises; }

    public void delete(Exercise exercise) { mRepository.delete(exercise);}

    public void insert(Exercise exercise) { mRepository.insert(exercise); }


}


