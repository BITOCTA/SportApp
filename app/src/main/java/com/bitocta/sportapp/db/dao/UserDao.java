package com.bitocta.sportapp.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.bitocta.sportapp.db.entity.User;

@Dao
public interface UserDao {


    @Delete
    void delete(User user);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

}
