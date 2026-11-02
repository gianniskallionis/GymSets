package com.giannis.workout_tracker.repository;

import com.giannis.workout_tracker.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository  extends JpaRepository<Workout,Long> {


    /**
     * we call it findByUserUsername because the class Workout does not have a username
     * but the class User has, so we call User and inside User we have username
     * @param username
     * @return
     */
    List<Workout> findByUserUsername(String username);
        // we find the workouts of the user
}
