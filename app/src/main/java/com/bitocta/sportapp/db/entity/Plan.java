package com.bitocta.sportapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import lombok.Data;

@Entity
@Data
public class Plan {

    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "plan_name")
    String name;

    @Embedded
    Exercise exercise;

    @ColumnInfo(name = "sets")
    int sets;

    @ColumnInfo(name = "reps")
    int reps;

    @ColumnInfo(name = "minutes")
    int minutes;

    @ColumnInfo(name = "secondsOfRest")
    int secondsOfRest;

    public Plan(String name, Exercise exercise, int sets, int reps, int minutes, int secondsOfRest) {
        this.name = name;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.secondsOfRest = secondsOfRest;
    }

    public Plan() {
    }
}
