package com.giannis.workout_tracker;


import com.giannis.workout_tracker.model.Exercise;
import com.giannis.workout_tracker.model.User;
import com.giannis.workout_tracker.model.Workout;
import com.giannis.workout_tracker.model.WorkoutSet;
import com.giannis.workout_tracker.repository.ExerciseRepository;
import com.giannis.workout_tracker.repository.UserRepository;
import com.giannis.workout_tracker.repository.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testSaveWorkout(){
        //1 Arrange
                // creating the workout
        User user= new User("giannis","test1234");//the user of the workout
        Workout workout= new Workout("push",2026,10,27);
        Exercise exerciseBench = new Exercise("Bench press", Exercise.Muscles.CHEST,
                new ArrayList<>(List.of(Exercise.Muscles.TRICEPS, Exercise.Muscles.FRONT_DELTS)));
        WorkoutSet setBench1= new WorkoutSet(exerciseBench,65.0,10);
        WorkoutSet setBench2=new WorkoutSet(exerciseBench,65.0,8);
        workout.addSet(setBench1);
        workout.addSet(setBench2);
        workout.setUser(user); // setting the user
        //2  ACT
        userRepository.save(user);  // saving the user in the db
        exerciseRepository.save(exerciseBench); // we need to save the exercise too , not only the workout
        Workout saved = workoutRepository.save(workout); // saving the workout to the db

        entityManager.flush();
        entityManager.clear();



        Workout found= workoutRepository.findById(saved.getId()).orElseThrow();


        // Assert
        // we check if the date is the same, if the volume is the same, and if the kg and reps are the same
        assertEquals(LocalDate.of(2026,10,27) ,found.getDate()      );

        Map<Exercise.Muscles, Double> setsMap=found.volume()    ; // a map that has key = muscle, value= the points of sets.

        assertEquals( 2.0            ,setsMap.get(Exercise.Muscles.CHEST),0.0001   ); //delta is the tolerance of error
        assertEquals(2.0/3,  setsMap.get(Exercise.Muscles.TRICEPS),0.0001); // to  accept that 2 numbers are equals
        assertEquals(2.0/3 ,  setsMap.get(Exercise.Muscles.FRONT_DELTS), 0.0001);

        assertEquals(65.0,found.getSets().get(0).getKg());
        assertEquals(65.0,found.getSets().get(1).getKg());
        assertEquals(10,found.getSets().get(0).getReps());
        assertEquals(8,found.getSets().get(1).getReps());




    }















}
