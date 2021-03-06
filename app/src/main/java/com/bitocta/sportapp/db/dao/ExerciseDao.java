package com.bitocta.sportapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bitocta.sportapp.db.entity.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    LiveData<List<Exercise>> getAll();


    @Delete
    void delete(Exercise exercise);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Exercise exercise);

}
