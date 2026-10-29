package com.giannis.workout_tracker.repository;

import com.giannis.workout_tracker.model.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet,Long> {
}

/*
IN repositories like this, the JPA repository has already implemented methods like save(x), findById(ID), findAll,
deleteById(id),count(), all i have to do is make an object of XRepository to use them
 */