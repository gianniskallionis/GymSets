package com.giannis.workout_tracker.repository;

import com.giannis.workout_tracker.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository  extends JpaRepository<Workout,Long> {


}
