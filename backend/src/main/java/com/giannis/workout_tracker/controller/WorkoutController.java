package com.giannis.workout_tracker.controller;


import com.giannis.workout_tracker.dto.CreateWorkoutRequest;
import com.giannis.workout_tracker.dto.WorkoutResponse;
import com.giannis.workout_tracker.model.Workout;
import com.giannis.workout_tracker.repository.WorkoutRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController       // all the returns inside the class goes to the client as Json ( through the api)
@RequestMapping("/workouts") // all methods in the class communicate with the api through the common /workouts
public class WorkoutController {

    private final WorkoutRepository workoutRepository;

    public WorkoutController(WorkoutRepository workoutRepository) {
        this.workoutRepository=workoutRepository;
    }


    @PostMapping               // requestBody turns the json input and turns them into java objects
    public WorkoutResponse createWorkout(@RequestBody CreateWorkoutRequest request ){

        Workout workoutCreated=new Workout(request.year(), request.month(), request.day()); // we create a new workout with the date
        workoutCreated=workoutRepository.save(workoutCreated); // we save it in the DB

        return new WorkoutResponse(workoutCreated.getId(), workoutCreated.getDate().getYear() ,workoutCreated.getDate().getMonthValue()
                , workoutCreated.getDate().getDayOfMonth());  // we return a workout object witht the id and the date

    }







}
