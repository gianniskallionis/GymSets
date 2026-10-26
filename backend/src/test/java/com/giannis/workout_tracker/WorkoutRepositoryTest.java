package com.giannis.workout_tracker;


import com.giannis.workout_tracker.model.Exercise;
import com.giannis.workout_tracker.model.Workout;
import com.giannis.workout_tracker.model.WorkoutSet;
import com.giannis.workout_tracker.repository.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.ArrayList;
import java.util.List;


@DataJpaTest
public class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void testSaveWorkout(){
        //1 Arrange
                // creating the workout
        Workout workout= new Workout(2026,10,26);
        Exercise exerciseBench = new Exercise("Bench press", Exercise.Muscles.CHEST,
                new ArrayList<>(List.of(Exercise.Muscles.TRICEPS, Exercise.Muscles.FRONT_DELTS)));
        WorkoutSet setBench1= new WorkoutSet(exerciseBench,65,10);
        WorkoutSet setBench2=new WorkoutSet(exerciseBench,65,8);
        workout.addSet(setBench1);
        workout.addSet(setBench2);
        //2  ACT
        Workout saved = workoutRepository.save(workout);

        entityManager.flush();   // saving the workout to the db
        entityManager.clear();



        Workout found= workoutRepository.findById(saved.getId()).orElseThrow();


        // Assert













    }















}
