package com.bitocta.sportapp.db.entity;

import android.util.Pair;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import lombok.Data;

@Entity
@Data
public class User {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    String email;

    @ColumnInfo(name = "username")
    String name;


    @ColumnInfo(name = "weight")
    double weight;

    @ColumnInfo(name = "height")
    double height;

    @ColumnInfo(name = "dateOfBirth")
    Date dateOfBirth;

    @ColumnInfo(name = "photo")
    String image_path;

    @Embedded
    Training activeTraining;

    @ColumnInfo(name="day")
    int day;

    @ColumnInfo(name = "plans")
    ArrayList<Training> plans;


    HashMap<String,Date> historyOfTrainings;

    HashMap<String,Double> historyOfWeight;


    long totalCalories;

    int totalTrainings;

    double totalMinutes;

    Date dateOfRegistration;




    public User() {
    }

    public User(String email) {
        this.email = email;
        dateOfRegistration = new Date();
    }

    public User(String name, double weight, double height, Date dateOfBirth, String image_path) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.image_path = image_path;
        this.activeTraining=null;
    }

    public User(String name, double weight, double height, Date dateOfBirth, String image_path, HashMap<String,Date> historyOfTrainings) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.image_path = image_path;
        this.activeTraining=null;
        this.historyOfTrainings = historyOfTrainings;
    }




    public void setActiveTraining(Training activeTraining) {
        this.activeTraining = activeTraining;
        setDay(0);
    }

    public void setDay(int day) {
        this.day = day;
    }
}
