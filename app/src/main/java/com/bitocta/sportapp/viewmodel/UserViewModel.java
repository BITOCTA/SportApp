package com.bitocta.sportapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.UserRepository;
import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.entity.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository mRepository;

    private LiveData<List<User>> mAllUsers;

    public UserViewModel (Application application) {
        super(application);
        mRepository = UserRepository.getInstance(AppDatabase.getDatabase(application));
        mAllUsers = mRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() { return mAllUsers; }

    public void delete(User user) { mRepository.delete(user);}

    public void insert(User user) { mRepository.insert(user); }

    public void update(User user) {mRepository.update(user);}
}
