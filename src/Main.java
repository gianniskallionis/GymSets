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
public class Μain {

    /**
     * Δημιουργεί ένα νέο αντικείμενο main.
     */
    public static void main(String[] args) {
    Exercise bench=new Exercise("Bench press",Exercise.Muscles.CHEST,  List.of(Exercise.Muscles.TRICEPS,Exercise.Muscles.FRONT_DELTS));
    System.out.println(bench.getNameOfExercise());
    System.out.println(bench.getPrimary());
    System.out.println(bench.getSecondaries());
    WorkoutSet set1= new WorkoutSet(bench,65,8);
    System.out.println(set1.getExercise().getNameOfExercise());
    System.out.println(set1.getKg());
    System.out.println(set1.getReps());

    }

    // Χρησιμοποίησε /** + Enter πάνω από μέθοδο για να δημιουργήσεις Javadoc skeleton
}
