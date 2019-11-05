package com.bitocta.sportapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bitocta.sportapp.db.entity.Training;
import com.bitocta.sportapp.db.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Delete
    void delete(User user);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

}
