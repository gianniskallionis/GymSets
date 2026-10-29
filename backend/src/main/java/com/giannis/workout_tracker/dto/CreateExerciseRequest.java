package com.giannis.workout_tracker.dto;

import com.giannis.workout_tracker.model.Exercise;

import java.util.List;

/**
 * this records stores the values that are Sent From the phone ( through the api)
 */
public record CreateExerciseRequest(String name, Exercise.Muscles primaryMuscle, List<Exercise.Muscles> listOfSecondaries ) {
}
