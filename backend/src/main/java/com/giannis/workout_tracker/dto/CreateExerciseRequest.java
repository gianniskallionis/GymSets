package com.giannis.workout_tracker.dto;

import com.giannis.workout_tracker.model.Exercise;

import java.util.List;

// dto means data transfer object, and we use Dto to transfer data between backEnd and frontEnd,

/*
 request means whatever data WE need from the client( phone) ex for the workoutSet we need the exercise ID
 and when we return a workoutSet to the client, we return a Response, and it has in it the workoutSet id, which did not
 exist when we received the  request, so request and response take different data
 */


/**
 * this records stores the values that are Sent From the phone ( through the api)
 */
public record CreateExerciseRequest(String name, Exercise.Muscles primaryMuscle, List<Exercise.Muscles> listOfSecondaries ) {
}
