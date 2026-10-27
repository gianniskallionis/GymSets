package com.giannis.workout_tracker.controller;


import com.giannis.workout_tracker.repository.WorkoutRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController       // all the returns inside the class goes to the client as Json ( through the api)
@RequestMapping("/workouts") // all methods in the class communicate with the api through the common /workouts
public class WorkoutController {

    private final WorkoutRepository workoutRepository;

    public WorkoutController(WorkoutRepository workoutRepository) {
        this.workoutRepository=workoutRepository;
    }


}
