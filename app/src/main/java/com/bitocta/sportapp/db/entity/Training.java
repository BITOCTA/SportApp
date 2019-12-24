package com.bitocta.sportapp.db.entity;

import android.util.Pair;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Entity
@Data
public class Training {

    @PrimaryKey(autoGenerate = true)
    public int tid;

    @ColumnInfo(name = "trainingname")
    String name;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "image")
    String image_path;

    @ColumnInfo(name = "days")
    int days;

    String level;

    int daysAWeek;

    @ColumnInfo(name = "setsOfExercises")
    ArrayList<ArrayList<Plan>> setsOfExercises;

    public Training(String name, String description, String image_path, String level, int daysAWeek, ArrayList<ArrayList<Plan>> setsOfExercises) {
        this.name = name;
        this.description = description;
        this.setsOfExercises = setsOfExercises;
        this.image_path = image_path;
        this.level = level;
        this.daysAWeek=daysAWeek;
        days=setsOfExercises.size();
    }

    public Training() {
    }
}
