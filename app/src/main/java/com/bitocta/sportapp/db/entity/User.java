package com.bitocta.sportapp.db.entity;

import android.util.Pair;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class User {

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "sex")
    String sex;

    @ColumnInfo(name = "weight")
    double weight;

    @ColumnInfo(name = "height")
    double height;

    @ColumnInfo(name = "dateOfBirth")
    Date dateOfBirth;

    @ColumnInfo(name = "activeTraining")
    Training activeTraining;

    @ColumnInfo(name = "plans")
    ArrayList<Training> plans;

    @ColumnInfo(name = "history")
    ArrayList<Pair<Training,Date>> history;


    public User(String name, String sex, double weight, double height, Date dateOfBirth) {
        this.name = name;
        this.sex = sex;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Training getActiveTraining() {
        return activeTraining;
    }

    public void setActiveTraining(Training activeTraining) {
        this.activeTraining = activeTraining;
    }

    public ArrayList<Training> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<Training> plans) {
        this.plans = plans;
    }

    public ArrayList<Pair<Training, Date>> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Pair<Training, Date>> history) {
        this.history = history;
    }
}
