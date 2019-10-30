package com.bitocta.sportapp.db.entity;

import android.util.Pair;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class Training {

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "dates")
    ArrayList<Date> dates;

    @ColumnInfo(name = "setsOfExercises")
    ArrayList<Pair<Exercise, Integer>> setsOfExercises;

    public Training(String name, String description, ArrayList<Date> dates, ArrayList<Pair<Exercise, Integer>> setsOfExercises) {
        this.name = name;
        this.description = description;
        this.dates = dates;
        this.setsOfExercises = setsOfExercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public ArrayList<Pair<Exercise, Integer>> getSetsOfExercises() {
        return setsOfExercises;
    }

    public void setSetsOfExercises(ArrayList<Pair<Exercise, Integer>> setsOfExercises) {
        this.setsOfExercises = setsOfExercises;
    }
}
