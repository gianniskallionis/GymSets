import java.util.*;
/**
 * main
 * <p>
 * Σύντομη περιγραφή της κλάσης main.
 *
 * @author giannis
 * @version 22-Oct-26
 * @since 2026
 */
public class Main {

    /**
     * Δημιουργεί ένα νέο αντικείμενο main.
     */
    public static void main(String[] args) {
        Exercise bench=new Exercise("Bench press",Exercise.Muscles.CHEST,  List.of(Exercise.Muscles.TRICEPS,Exercise.Muscles.FRONT_DELTS));
        Exercise squat=new Exercise("Squat",Exercise.Muscles.QUADS,List.of(Exercise.Muscles.GLUTES,Exercise.Muscles.ADDUCTORS));
        System.out.println(bench.getNameOfExercise());
        System.out.println(bench.getPrimary());
        System.out.println(bench.getSecondaries());
        WorkoutSet set1= new WorkoutSet(bench,65,8);
        System.out.println(set1.getExercise().getNameOfExercise());
        System.out.println(set1.getKg());
        System.out.println(set1.getReps());

        Workout w = new Workout(2026,10,22);
        WorkoutSet set2=new WorkoutSet(bench,60,10);
        WorkoutSet set3=new WorkoutSet(squat,55,11);

        w.addSet(set1);
        w.addSet(set2);
        w.addSet(set3);


        System.out.println(set2.getExercise().getNameOfExercise());
        System.out.println(set2.getKg());
        System.out.println(set2.getReps());


        System.out.println(set3.getExercise().getNameOfExercise());
        System.out.println(set3.getKg());
        System.out.println(set3.getReps());

        // the map is a local variable of the method volume() therefore whenever we need it
        // we call volume()
        for (Map.Entry<Exercise.Muscles,Double> entry : w.volume().entrySet() ) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());


        }








    }

    // Χρησιμοποίησε /** + Enter πάνω από μέθοδο για να δημιουργήσεις Javadoc skeleton
}
