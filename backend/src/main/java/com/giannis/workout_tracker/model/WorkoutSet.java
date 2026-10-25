package com.giannis.workout_tracker.model;

import jakarta.persistence.*;

import java.util.*;

/**
 * WorkoutSet

 */
@Entity  // we have a table Workoutset with the Entity
public class WorkoutSet {

    // we need an id for the table
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private double kg;
    private int reps;
    @ManyToOne(optional = false) // optional=false means that there MUST be an exercise, can not be null.
    // here we state that many (WORKOUTSETS) are corresponded to ONE exercise
    private Exercise exercise; // i need setter/getter for exercise
    @ManyToOne(optional = false)
    private Workout workout ; // creates  the column "workout_id" in the table WorkoutSet


    public Long getId(){
        return id;
    }

    protected WorkoutSet(){}// we need an empty constructor so hibernate can read an empty row from the db
    // and put the values later

    public WorkoutSet(Exercise exercise,double kg, int reps) {
        setExercise(exercise);
        setKg(kg);
        setReps(reps);
    }

    public double getKg() {
        return kg;
    }

    public void setKg(double kg) {
        if(kg<0){
            throw new IllegalArgumentException("PROBLEM:Weight of exercise cannot be <0 ");}
        this.kg = kg;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        if(reps<0){throw new IllegalArgumentException("PROBLEM:Reps of exercise can not be <0");}
        this.reps = reps;
    }

    public Exercise getExercise()
    {
        return exercise;
    }

    /**
     *
     * @param exercise: the object exercise which has the variables(name,primary,secondaries) in it .
     *The set has been created in main and it is passed here as a parameter
     */
    public void setExercise(Exercise exercise){
        Objects.requireNonNull(exercise,"PROBLEM: exercise is null");
        this.exercise=exercise;
    }

    public void setWorkout(Workout workout){
        Objects.requireNonNull(workout,"PROBLEM:workout is null");
        this.workout=workout;
    }




}
