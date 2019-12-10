package com.bitocta.sportapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Entity
@Data
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    public int eid;

    @ColumnInfo(name = "exercise_name")
    String name;

    @ColumnInfo(name = "imagePath")
    String imagePath;

    @ColumnInfo(name = "type")
    ExerciseType type;

    @ColumnInfo(name = "level")
    ExerciseLevel level;

    @ColumnInfo(name = "muscles")
    ArrayList<ExerciseMuscles> muscles;

    @ColumnInfo(name = "equipment")
    ExerciseEquipment equipment;

    public Exercise(String name, String imagePath, ExerciseType type, ExerciseLevel level, ArrayList<ExerciseMuscles> muscles, ExerciseEquipment equipment) {
        this.name = name;
        this.imagePath = imagePath;
        this.type = type;
        this.level = level;
        this.muscles = muscles;
        this.equipment = equipment;
    }

    public Exercise() {
    }

    public enum ExerciseLevel {
        BEGINNER,
        INTERMEDIATE,
        PROFESSIONAL
    }

    public enum ExerciseMuscles {
        CHEST,
        FOREARMS,
        LATS,
        MIDDLE_BACK,
        LOWER_BACK,
        NECK,
        QUADRICEPS,
        HAMSTRINGS,
        CALVES,
        TRICEPS,
        TRAPS,
        SHOULDERS,
        ABDOMINALS,
        GLUTES,
        BICEPS,
        ADDUCTORS,
        ABDUCTORS,
        LEGS
    }

    public enum ExerciseType{
        CARDIO,
        WEIGHTLIFTING,
        PLYOMETRICS,
        POWERLIFTING,
        STRENGTH,
        STRETCHING,
    }


    public enum ExerciseEquipment{
        BANDS,
        FOAM_ROLL,
        BARBELL,
        KETTLEBELLS,
        BODY_ONLY,
        MACHINE,
        CABLE,
        MEDICINE_BALL,
        DUMBBELL,
        NONE,
        OTHER
    }


}
