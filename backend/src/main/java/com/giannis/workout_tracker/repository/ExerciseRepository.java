package com.giannis.workout_tracker.repository;

import com.giannis.workout_tracker.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {
}
