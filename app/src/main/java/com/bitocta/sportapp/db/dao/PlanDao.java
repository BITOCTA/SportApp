package com.bitocta.sportapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bitocta.sportapp.db.entity.Plan;
import com.bitocta.sportapp.db.entity.Training;

import java.util.List;

@Dao
public interface PlanDao {
    @Query("SELECT * FROM `plan`")
    LiveData<List<Plan>> getAll();

    @Delete
    void delete(Plan plan);

    @Insert
    void insert(Plan plan);

    @Update
    void update(Plan plan);

}
