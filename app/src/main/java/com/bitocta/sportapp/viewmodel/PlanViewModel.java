package com.bitocta.sportapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.ExerciseRepository;
import com.bitocta.sportapp.PlanRepository;
import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.entity.Exercise;
import com.bitocta.sportapp.db.entity.Plan;
import com.bitocta.sportapp.db.entity.Training;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlanViewModel extends AndroidViewModel {

    private static final String TAG = "DB_OPERATION_PLAN";

    private PlanRepository mRepository;

    private LiveData<List<Plan>> mAllPlans;

    public PlanViewModel (Application application) {
        super(application);
        mRepository = PlanRepository.getInstance(AppDatabase.getDatabase(application));
        mAllPlans = mRepository.getAllPlans();
    }

    public LiveData<List<Plan>> getAllPlans() { return mAllPlans; }

    public void delete(Plan plan) { Completable.fromAction(() -> mRepository.delete(plan)).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {

                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "PLAN delete Successful");
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "PLAN delete Error: " + e.getMessage());
                }
            }); }

    public void insert(Plan plan) {   Completable.fromAction(() -> mRepository.insert(plan)).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {

                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "PLAN Insert Successful");
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "PLAN Insert Error: " + e.getMessage());
                }
            }); }


}
