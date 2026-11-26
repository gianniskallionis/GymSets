package com.giannis.workout_tracker.dto;


/**
 *  Α record is a class that automatically creates : constructor, Getter,ToString,Hash, equals
 *  for your variables. but the variables are Immutable( meaning once initialized can not be changed)
 *
 * we use a record here to keep that data that we receive from the phone through the Api
 *
 */
public record CreateWorkoutRequest(String name,int year, int month, int day) {

}
