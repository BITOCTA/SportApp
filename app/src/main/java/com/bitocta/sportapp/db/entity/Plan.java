package com.bitocta.sportapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Plan {

    @PrimaryKey(autoGenerate = true)
    public int pid;

    @Embedded
    Exercise exercise;

    @ColumnInfo(name = "sets")
    int sets;

    @ColumnInfo(name = "reps")
    int reps;

    @ColumnInfo(name = "secondsOfRest")
    int secondsOfRest;

    public Plan(Exercise exercise, int sets, int reps, int secondsOfRest) {
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.secondsOfRest = secondsOfRest;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSecondsOfRest() {
        return secondsOfRest;
    }

    public void setSecondsOfRest(int secondsOfRest) {
        this.secondsOfRest = secondsOfRest;
    }
}
