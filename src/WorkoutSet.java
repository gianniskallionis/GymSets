import java.util.*;

/**
 * WorkoutSet

 */
public class WorkoutSet {

    private double kg;
    private int reps;
    private Exercise exercise; // i need setter/getter for exercise


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





}
