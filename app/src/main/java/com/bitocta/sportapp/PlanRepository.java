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

    public void insert (Plan exercise) {
        new PlanRepository.insertAsyncTask(mPlanDao).execute(exercise);
    }
    public void delete (Plan plan) { new PlanRepository.deleteAsyncTask(mPlanDao).execute(plan);}
    public void update (Plan plan) {new PlanRepository.updateAsyncTask(mPlanDao).execute(plan);}

    private static class insertAsyncTask extends AsyncTask<Plan, Void, Void> {

        private PlanDao mAsyncTaskDao;

        insertAsyncTask(PlanDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Plan... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Plan, Void, Void>{
        private PlanDao mAsyncTaskDao;

        deleteAsyncTask(PlanDao dao) {mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Plan... params){
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Plan, Void, Void> {
        private PlanDao mAsyncTaskDao;

        updateAsyncTask(PlanDao dao) { mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Plan... params){
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

}
