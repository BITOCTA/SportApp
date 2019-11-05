package com.bitocta.sportapp;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bitocta.sportapp.db.AppDatabase;
import com.bitocta.sportapp.db.dao.UserDao;
import com.bitocta.sportapp.db.entity.User;

import java.util.List;

public class UserRepository {

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

    public void insert (User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }
    public void delete (User user) { new deleteAsyncTask(mUserDao).execute(user);}
    public void update (User user) {new updateAsyncTask(mUserDao).execute(user);}

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao mAsyncTaskDao;

        deleteAsyncTask(UserDao dao) {mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final User... params){
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mAsyncTaskDao;

        updateAsyncTask(UserDao dao) { mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final User... params){
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}


