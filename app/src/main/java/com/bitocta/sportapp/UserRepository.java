package com.bitocta.sportapp;

import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.dao.UserDao;
import com.bitocta.sportapp.db.entity.User;

import java.util.List;

public class UserRepository  {

    private static UserRepository instance;
    private final AppDatabase database;

    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    UserRepository(final AppDatabase database){
        this.database=database;
        mUserDao=this.database.userDao();
        mAllUsers = mUserDao.getAll();
    }

    public static UserRepository getInstance(final AppDatabase database) {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository(database);
                }
            }
        }
        return instance;
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public User findUserWithName(String search){return mUserDao.findUserWithName(search);}

    public void insert (User user) {
        mUserDao.insert(user);
    }
    public void delete (User user) { mUserDao.delete(user);}





}


