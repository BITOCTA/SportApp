package com.bitocta.sportapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Exercise {

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "imagePath")
    String imagePath;

    @ColumnInfo(name = "type")
    ExerciseType type;

    @ColumnInfo(name = "level")
    ExerciseLevel level;

    @ColumnInfo(name = "muscles")
    ExerciseMuscles muscles;

    @ColumnInfo(name = "equipment")
    ExerciseEquipment equipment;

    public Exercise(String name, String imagePath, ExerciseType type, ExerciseLevel level, ExerciseMuscles muscles, ExerciseEquipment equipment) {
        this.name = name;
        this.imagePath = imagePath;
        this.type = type;
        this.level = level;
        this.muscles = muscles;
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public ExerciseLevel getLevel() {
        return level;
    }

    public void setLevel(ExerciseLevel level) {
        this.level = level;
    }

    public ExerciseMuscles getMuscles() {
        return muscles;
    }

    public void setMuscles(ExerciseMuscles muscles) {
        this.muscles = muscles;
    }

    public ExerciseEquipment getEquipment() {
        return equipment;
    }

    public void setEquipment(ExerciseEquipment equipment) {
        this.equipment = equipment;
    }

    enum ExerciseLevel {
        BEGINNER,
        INTERMEDIATE,
        PROFESSIONAL
    }

    enum ExerciseMuscles {
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
        ABDUCTORS
    }

    enum ExerciseType{
        CARDIO,
        WEIGHTLIFTING,
        PLYOMETRICS,
        POWERLIFTING,
        STRENGTH,
        STRETCHING,
    }


    enum ExerciseEquipment{
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
