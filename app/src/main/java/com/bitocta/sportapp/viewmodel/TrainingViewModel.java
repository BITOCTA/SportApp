package com.bitocta.sportapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.PlanRepository;
import com.bitocta.sportapp.TrainingRepository;
import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.entity.Training;
import com.bitocta.sportapp.db.entity.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TrainingViewModel extends AndroidViewModel {

    private static final String TAG = "DB_OPERATION_TRAINING";

    private TrainingRepository mRepository;

    private LiveData<List<Training>> mAllTrainings;

    public TrainingViewModel (Application application) {
        super(application);
        mRepository = TrainingRepository.getInstance(AppDatabase.getDatabase(application));
        mAllTrainings = mRepository.getAllTrainings();
    }

    public LiveData<List<Training>> getAllTrainings() { return mAllTrainings; }

    public void delete(Training training) { Completable.fromAction(() -> mRepository.delete(training)).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {

                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "TRAINING delete Successful");
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "TRAINING delete Error: " + e.getMessage());
                }
            }); }

    public void insert(Training training) {   Completable.fromAction(() -> mRepository.insert(training)).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {

                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "TRAINING Insert Successful");
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "TRAINING Insert Error: " + e.getMessage());
                }
            }); }

}
