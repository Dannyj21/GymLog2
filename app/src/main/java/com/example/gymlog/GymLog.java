
package com.example.gymlog;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gym_logs")
public class GymLog {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private String exercise;
    private int reps;

    public GymLog(int userId, String exercise, int reps) {
        this.userId = userId;
        this.exercise = exercise;
        this.reps = reps;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getExercise() { return exercise; }
    public void setExercise(String exercise) { this.exercise = exercise; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    @Override
    public String toString() {
        return "User " + userId + " - " + exercise + " (" + reps + " reps)";
    }
}