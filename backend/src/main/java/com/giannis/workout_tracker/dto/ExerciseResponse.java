package com.giannis.workout_tracker.dto;

import com.giannis.workout_tracker.model.Exercise;

import java.util.List;

/**
 * we store the data that we SEND to the phone ( client) through the api
 *
 */
public record ExerciseResponse(Long id , String name, Exercise.Muscles primaryMuscle, List<Exercise.Muscles> listOfSecondaries ) {
}
