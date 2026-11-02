package com.giannis.workout_tracker.controller;

import com.giannis.workout_tracker.model.WorkoutSet;
import com.giannis.workout_tracker.repository.UserRepository;
import com.giannis.workout_tracker.repository.WorkoutRepository;
import com.giannis.workout_tracker.repository.WorkoutSetRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/sets")
public class WorkoutSetController {
    private final WorkoutSetRepository workoutSetRepository;// we use the data of the Set
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;


    @DeleteMapping("/{setId}")
    public void DeleteWorkoutSet(@PathVariable Long setId, Authentication authentication){
        WorkoutSet setOfTheWorkout= workoutSetRepository.findByIdAndWorkoutUserUsername(setId, authentication.getName())
                .orElseThrow(()->new NoSuchElementException("Set "+setId+" not found"));// we authenticate that the user deletes his own sets

            workoutSetRepository.deleteById(setId);
            // finds the workoutSet from the repo  using its id and it deletes it
        }



    public WorkoutSetController(WorkoutSetRepository workoutSetRepository, UserRepository userRepository, WorkoutRepository workoutRepository) {
        this.workoutSetRepository = workoutSetRepository;

        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
    }


}
