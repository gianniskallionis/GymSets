package com.giannis.workout_tracker.repository;

import com.giannis.workout_tracker.model.Exercise;
import com.giannis.workout_tracker.model.Workout;
import com.giannis.workout_tracker.model.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {
    List<Exercise> findByUserUsername(String username);
    // we find the exercises of the user

    Optional<Exercise> findByIdAndUserUsername(Long id, String username);
} //                     it finds  the exercise that has : the id given of the set AND
//                                        the username given of the class workout





