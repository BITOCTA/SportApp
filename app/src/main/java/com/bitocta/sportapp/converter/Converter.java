package com.bitocta.sportapp.converter;

import android.text.TextUtils;
import android.util.Pair;

import androidx.room.TypeConverter;

import com.bitocta.sportapp.db.entity.Exercise;
import com.bitocta.sportapp.db.entity.Plan;
import com.bitocta.sportapp.db.entity.Training;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Converter {


    @TypeConverter
    public static ArrayList<Exercise.ExerciseMuscles> fromStringExerciseMuscles(String value) {
        Type listType = new TypeToken<ArrayList<Exercise.ExerciseMuscles>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListExerciseMuscles(ArrayList<Exercise.ExerciseMuscles> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static ArrayList<Training> fromStringTrainings(String value) {
        Type listType = new TypeToken<ArrayList<Training>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListTrainings(ArrayList<Training> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static ArrayList<Plan> fromStringPlans(String value) {
        Type listType = new TypeToken<ArrayList<Plan>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String fromArrayListPlans(ArrayList<Plan> list) {
        Gson gson = new Gson();

        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static ArrayList<ArrayList<Plan>> fromStringSets(String value) {
        Type listType = new TypeToken<ArrayList<ArrayList<Plan>>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListSets(ArrayList<ArrayList<Plan>> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


    @TypeConverter
    public static ArrayList<Date> fromStringDates(String value) {
        Type listType = new TypeToken<ArrayList<Date>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListDates(ArrayList<Date> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static  HashMap<String,Date>  fromStringHistory(String value){
        Type listType = new TypeToken<HashMap<String,Date>>() {}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String fromArrayListHistory(HashMap<String,Date> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


    @TypeConverter
    public static HashMap<String,Double> fromStringHistoryW(String value){
        Type listType = new TypeToken<HashMap<String,Double>>() {}.getType();
        return new Gson().fromJson(value,listType);
    }





    @TypeConverter
    public static String fromArrayListHistoryW(HashMap<String,Double> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }





    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromExerciseLevel(Exercise.ExerciseLevel exerciseLevel) {
        if (exerciseLevel == null) {
            return null;
        }
        return exerciseLevel.name();
    }
    @TypeConverter
    public static Exercise.ExerciseLevel toExerciseLevel(String  exerciseLevel){
        if(TextUtils.isEmpty(exerciseLevel))
            return null;
        return Exercise.ExerciseLevel.valueOf(exerciseLevel);
    }

    @TypeConverter
    public static String fromExerciseMuscles(Exercise.ExerciseMuscles exerciseMuscles) {
        if (exerciseMuscles == null) {
            return null;
        }
        return exerciseMuscles.name();
    }
    @TypeConverter
    public static Exercise.ExerciseMuscles toExerciseMuscles(String exerciseMuscles ){
        if(TextUtils.isEmpty(exerciseMuscles))
            return null;
        return Exercise.ExerciseMuscles.valueOf(exerciseMuscles);
    }

    @TypeConverter
    public static String fromExerciseType(Exercise.ExerciseType exerciseType) {
        if (exerciseType == null) {
            return null;
        }
        return exerciseType.name();
    }
    @TypeConverter
    public static Exercise.ExerciseType toExerciseType(String  exerciseType){
        if(TextUtils.isEmpty(exerciseType))
            return null;
        return Exercise.ExerciseType.valueOf(exerciseType);
    }

    @TypeConverter
    public static String fromExerciseEquipment(Exercise.ExerciseEquipment exerciseEquipment) {
        if (exerciseEquipment == null) {
            return null;
        }
        return exerciseEquipment.name();
    }
    @TypeConverter
    public static Exercise.ExerciseEquipment toExerciseEquipment(String  exerciseEquipment){
        if(TextUtils.isEmpty(exerciseEquipment))
            return null;
        return Exercise.ExerciseEquipment.valueOf(exerciseEquipment);
    }
}
