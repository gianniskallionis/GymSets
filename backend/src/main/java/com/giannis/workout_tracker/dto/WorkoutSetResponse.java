package com.giannis.workout_tracker.dto;

/**
 * here we have the data that we send back to the client
 */
public record WorkoutSetResponse(Long setId,Long exerciseId,double kg, int reps ) {
}
