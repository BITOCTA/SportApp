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

    @ColumnInfo(name = "minutes")
    int seconds;

    @ColumnInfo(name = "secondsOfRest")
    int secondsOfRest;

    public Plan(String name, Exercise exercise, int seconds, int secondsOfRest) {
        this.name = name;
        this.exercise = exercise;
        this.seconds = seconds;
        this.secondsOfRest = secondsOfRest;
    }

    public Plan() {
    }
}
