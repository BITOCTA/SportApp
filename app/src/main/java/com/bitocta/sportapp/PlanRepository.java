package com.bitocta.sportapp;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.dao.ExerciseDao;
import com.bitocta.sportapp.db.dao.PlanDao;
import com.bitocta.sportapp.db.dao.TrainingDao;
import com.bitocta.sportapp.db.dao.UserDao;
import com.bitocta.sportapp.db.entity.Exercise;
import com.bitocta.sportapp.db.entity.Plan;
import com.bitocta.sportapp.db.entity.Training;
import com.bitocta.sportapp.db.entity.User;

import java.util.List;

public class PlanRepository {

    private static PlanRepository instance;
    private final AppDatabase database;

    private PlanDao mPlanDao;
    private LiveData<List<Plan>> mAllPlans;

    PlanRepository(final AppDatabase database){
        this.database=database;
        mPlanDao=this.database.planDao();
        mAllPlans = mPlanDao.getAll();
    }

    public static PlanRepository getInstance(final AppDatabase database) {
        if (instance == null) {
            synchronized (PlanRepository.class) {
                if (instance == null) {
                    instance = new PlanRepository(database);
                }
            }
        }
        return instance;
    }



    public LiveData<List<Plan>> getAllPlans() {
        return mAllPlans;
    }

    public void insert (Plan plan) {
        mPlanDao.insert(plan);
    }
    public void delete (Plan plan) { mPlanDao.delete(plan);}

}
