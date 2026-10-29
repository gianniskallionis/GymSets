package com.giannis.workout_tracker.controller;

import com.giannis.workout_tracker.model.WorkoutSet;
import com.giannis.workout_tracker.repository.WorkoutSetRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sets")
public class WorkoutSetController {
    private final WorkoutSetRepository workoutSetRepository;  // we use the data of the Set


    @DeleteMapping("/{setId}")
    public void DeleteWorkoutSet(@PathVariable Long setId ){
        workoutSetRepository.deleteById(setId);
        // finds the workoutSet from the repo  using its id and it deletes it

    }


    public WorkoutSetController(WorkoutSetRepository workoutSetRepository) {
        this.workoutSetRepository = workoutSetRepository;
    }


}
