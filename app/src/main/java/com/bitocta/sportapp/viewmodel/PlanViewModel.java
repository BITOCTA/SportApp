package com.bitocta.sportapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.ExerciseRepository;
import com.bitocta.sportapp.PlanRepository;
import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.entity.Exercise;
import com.bitocta.sportapp.db.entity.Plan;

import java.util.List;

public class PlanViewModel extends AndroidViewModel {
    private PlanRepository mRepository;

    private LiveData<List<Plan>> mAllPlans;

    public PlanViewModel (Application application) {
        super(application);
        mRepository = PlanRepository.getInstance(AppDatabase.getDatabase(application));
        mAllPlans = mRepository.getAllPlans();
    }

    public LiveData<List<Plan>> getAllPlans() { return mAllPlans; }

    public void delete(Plan plan) { mRepository.delete(plan);}

    public void insert(Plan plan) { mRepository.insert(plan); }


}
