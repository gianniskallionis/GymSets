package com.giannis.workout_tracker.model;

import java.time.*;
import java.util.*;

public class Workout {

    private LocalDate date;

    private List<WorkoutSet> sets ;


    /**
     * we create the date for the workout, and we record the sets in the sets list.
     * SOS SOS SOS ::: Had i written in the constructor: List<WorkoutSet> sets=new ArrayList<>();
     * instead of : sets=new ArrayList<>();, the constructor would had created a new variable sets
     * that shadowed the original one, thats why i need to not put the type in front of the sets for a
     * second time
     *
     *
     *
     *
     *
     *
     */
    public Workout(int year,int month, int day) {
        date=LocalDate.of( year,  month,  day);
         sets =new ArrayList<WorkoutSet>();
    }
    public void addSet(WorkoutSet set1){
        if(set1==(null)  ){throw new NullPointerException("PROBLEM: Set is null");}
        this.sets.add(set1);}

    /** i have a map Volume of < Muscle, Double> (ex: <Chest,10> ,  for every muscle that is
     * present on the sets , i will add the volume to the map . for primary its 1, for secondary its 0.33
     * to add the sets. the getOrDefault method ensures the map is initiated with 0 as its value to not have null values
     * setsPerMuscle is a local variable, whenever we need the map we must call the method.
     */
    public Map<Exercise.Muscles, Double> volume() {
         Map<Exercise.Muscles, Double> setsPerMuscle = new EnumMap<>(Exercise.Muscles.class);
        for (WorkoutSet currentSet : sets) {
            Exercise.Muscles keyPrimary = currentSet.getExercise().getPrimary();
            setsPerMuscle.put(keyPrimary, setsPerMuscle.getOrDefault(keyPrimary, 0.0) + 1);
            // we ADD the sets of the primary muscle by 1 .

            List<Exercise.Muscles> listSecondaries = currentSet.getExercise().getSecondaries();
            // we make a list of the secondary muscles
            for (Exercise.Muscles currentMuscle : listSecondaries) {  // we traverse the list of muscles that are secondary
                setsPerMuscle.put(currentMuscle, setsPerMuscle.getOrDefault(currentMuscle, 0.0) + 1.0 / 3);
                //          key of the map
            }
        }
            return setsPerMuscle;
    }






}