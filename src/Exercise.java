import java.util.*;
public class Exercise {
    /**
     *  the class contains the name of the exercises and the muscles it uses
     *
     *
     *  TO DO : make deep copy of the list for primary and secondaries so it has defensive copies
     *
     */


private String nameOfExercise;
private Muscles primary;
private List<Muscles>secondaries;


    /**
     * the muscles are enumerated and specific
     */
    enum Muscles {
    TRAPS, RHOMBOIDS, LATS, ERECTORS, FRONT_DELTS, SIDE_DELTS, REAR_DELTS, BICEPS,
    TRICEPS, FOREARMS, CHEST, ABS, QUADS, GLUTES, HAMSTRINGS, CALVES, ADDUCTORS}

    /**
     * sets the name of the exercise
     * @param name the name of the exercise, it must not be null .
     *  SOS ABOUT THE NULLS:  the constructor's obligation is to NOT create the object if the variable is null,
     *   it is the Frontend's responsibility to ask the user to re enter the data so the variable is correct, you do not ask
     *   it the class, in the class you only throw execption.
     * we use the isBlank() and not the isEmpty() to ensure that any whitespaces is disergaded

     */
    public void setNameOfExercise(String name){
        Objects.requireNonNull(name,"PROBLEM:name of excercise is null");
        if(name.isBlank()){
            throw new IllegalArgumentException("PROBLEM:Blank name");
        }
        nameOfExercise=name.trim();}


    /**
     * we use the if -> throw method to discover the exception and  ensure valid variables,
     * The  try/catch method is what you use when you WANT to DECIDE what to do with the exception.
     * It is not needed here.
     * Also since the muscles are chosen from the list, we dont have to check for blank.
     * @param primary : the primary muscle of the exercise
     */
    public void setPrimary(Muscles primary){
        Objects.requireNonNull(primary,"PROBLEM:primary muscle is null");

        this.primary=primary;
    }
    public void setSecondaries(List<Muscles> secondaries){
        Objects.requireNonNull(secondaries,"PROBLEM:secondaries muscle is null");
        this.secondaries=secondaries;
    }

    public Muscles getPrimary(){
        return primary;
    }

    public List<Muscles> getSecondaries(){
        return secondaries;
    }
    public String getNameOfExercise(){
    return nameOfExercise;
    }






    public Exercise(String name,Muscles primary,List<Muscles>list1) {
        setNameOfExercise(name);
        setPrimary(primary);
        setSecondaries(list1);
    }


}
