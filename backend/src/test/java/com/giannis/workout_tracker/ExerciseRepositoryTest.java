package com.giannis.workout_tracker;

import java.util.*;
import com.giannis.workout_tracker.model.Exercise;
import com.giannis.workout_tracker.repository.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 *
 */

@DataJpaTest
public class ExerciseRepositoryTest {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired  // entity manager is that talks to the DB
    private TestEntityManager entityManager;


    @Test
    public void testSaveExercise() {  // 1) Arrange: set up the preconditions for the test
        Exercise exerciseBench = new Exercise("Bench press", Exercise.Muscles.CHEST,
                new ArrayList<>(List.of(Exercise.Muscles.TRICEPS, Exercise.Muscles.FRONT_DELTS)));
        // when we give the list in an argument we make a new array list so the list is mutable.

        //2) Act : do the test.  // the save method is inherited from the JpaRepository  through ExerciseRepository
        // ,it creates a new line in the table in the DB , the new line is the exercise.
        Exercise saved= exerciseRepository.save(exerciseBench); // the exercise saved to the DB

        // if not forced the manager will delay the writing on the DB, with flush n clear we force the manager
        // to do the writing right now so we can see if we can retrieve the values from the actual DB and not memory
        entityManager.flush(); //  writes the INSERT on the DB
        entityManager.clear(); // deletes the objects from the memory so we HAVE to get them from the DB


        Exercise  found= exerciseRepository.findById(saved.getId())  .orElseThrow() ;

        //3) Assert ,checks if the name we saved is the same as the one we retrieved from the db


        assertEquals("Bench press",found.getNameOfExercise());

        assertEquals(Exercise.Muscles.CHEST,found.getPrimaryMuscle());

        assertEquals(2,found.getSecondaries().size());
        assertTrue(found.getSecondaries().  contains(Exercise.Muscles.FRONT_DELTS) );
        assertTrue(found.getSecondaries().  contains(Exercise.Muscles.TRICEPS)     );






} }





