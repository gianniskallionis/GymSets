package com.giannis.workout_tracker.dto;

/**
 * here we obtain the data from the client that creates a set
 *
 */
public record CreateWorkoutSetRequest(Long exerciseId,double kg, int reps) {
}
