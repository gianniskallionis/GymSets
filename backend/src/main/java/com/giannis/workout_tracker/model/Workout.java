package com.giannis.workout_tracker.model;

import jakarta.persistence.*;

import java.time.*;
import java.util.*;
@Entity  // we make workout an entity so we can have an array of it in the DB
public class Workout {

    private LocalDate date;
    @Id  // the id of the workout for the table
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public Long getId(){
        return id;}

    @ManyToOne(optional=false)
    private User user;
    public void setUser(User user){
        if(user==null){throw new NullPointerException("PROBLEM:User is null");}
        this.user=user;
    }

    public User getUser(){
            return user;
        }
    public String name;
    public String getName(){return name;}

    public void setName(String name){
        if(name!=null){this.name=name;}
        else{this.name="workout";}
    }


    // cascade is what happens to childen entities when a father entity is altered
        @OneToMany(cascade=CascadeType.ALL,                    orphanRemoval = true                 ,mappedBy ="workout" )  // 1 workout corresponds to N workoutSETS
        //  cascadeType.ALL = we have all the funcions of the cascade,orphanRemoval means that when a set is without a workout, its deleted                mapped by = workout means that the relation is
        // lead by WorkoutSet and its variable name to connect with this class is workout

        @OrderBy() // created orderBy so the DB can return the sets back in the order i created them ( older returns first)
    private List<WorkoutSet> sets ;

    public List<WorkoutSet> getSets(){  // getter for the sets
        return  new ArrayList<>(sets);
    }

    protected Workout(){}// empty constructor so hibernate can give values to the empty objects

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
    public Workout(String name,int year,int month, int day) {
        date=LocalDate.of( year,  month,  day);
        setName(name);
         sets =new ArrayList<WorkoutSet>();
    }

    /**
     *  getter for the date, to use in Tests
     *
     */
    public LocalDate getDate(){
        return date;
    }




    public void addSet(WorkoutSet set1){
        if(set1==(null)  ){throw new NullPointerException("PROBLEM: Set is null");}
        this.sets.add(set1);
        set1.setWorkout(this);  // we go to the set1 instance and set its workout as the one we are currently in, thus LINKING them
    }


    /** i have a map Volume of < Muscle, Double> (ex: <Chest,10> ,  for every muscle that is
     * present on the sets , i will add the volume to the map . for primary its 1, for secondary its 0.33
     * to add the sets. the getOrDefault method returns 0.0 when there is no key  so we do  not have null values
     * setsPerMuscle is a local variable, whenever we need the map we must call the method.
     */
    public Map<Exercise.Muscles, Double> volume() {
         Map<Exercise.Muscles, Double> setsPerMuscle = new EnumMap<>(Exercise.Muscles.class);
        for (WorkoutSet currentSet : sets) {
            Exercise.Muscles keyPrimary = currentSet.getExercise().getPrimaryMuscle();
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