package com.bitocta.sportapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bitocta.sportapp.db.entity.Training;

import java.util.List;

@Dao
public interface TrainingDao {

    @Query("SELECT * FROM training")
    LiveData<List<Training>> getAll();

    @Delete
    void delete(Training training);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Training training);



}
