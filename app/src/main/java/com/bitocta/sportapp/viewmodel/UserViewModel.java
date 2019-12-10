package com.bitocta.sportapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.UserRepository;
import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.entity.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends AndroidViewModel {

    private static final String TAG = "DB_OPERATION_USER";

    private UserRepository mRepository;

    private LiveData<List<User>> mAllUsers;

    public UserViewModel(Application application) {
        super(application);
        mRepository = UserRepository.getInstance(AppDatabase.getDatabase(application));
        mAllUsers = mRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void delete(User user) {
        Completable.fromAction(() -> mRepository.delete(user)).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {

            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "USER delete Successful");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "USER delete Error: " + e.getMessage());
            }
        });
    }

    public void insert(User user) {
        Completable.fromAction(() -> mRepository.insert(user)).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "USER Insert Successful");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "USER Insert Error: " + e.getMessage());
            }
        });
    }


}
