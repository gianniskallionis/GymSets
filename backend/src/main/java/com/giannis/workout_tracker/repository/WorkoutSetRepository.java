package com.giannis.workout_tracker.repository;

import com.giannis.workout_tracker.model.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet,Long> {

    Optional<WorkoutSet> findByIdAndWorkoutUserUsername(Long id, String username);
} //                     it find the id of the workoutSet that has : the id given of the set AND
//                                        the username given of the class workout

/*
IN repositories like this, the JPA repository has already implemented methods like save(x), findById(ID), findAll,
deleteById(id),count(), all i have to do is make an object of XRepository to use them
 */