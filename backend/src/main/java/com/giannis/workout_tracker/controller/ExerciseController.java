package com.giannis.workout_tracker.controller;


import com.giannis.workout_tracker.dto.ExerciseResponse;
import com.giannis.workout_tracker.dto.CreateExerciseRequest;
import com.giannis.workout_tracker.model.Exercise;
import com.giannis.workout_tracker.repository.ExerciseRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // the return goes back as Json, to the client
@RequestMapping("/exercises") // the methods of the class share the common /Exercise in the link
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;  // the repository we save and take from the data we use

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @PostMapping
    public ExerciseResponse createExercise(@RequestBody CreateExerciseRequest request){
         // the request body turns the incoming data from JSON form to java objects
        // we get the data from the phone  (client)  through the createExerciseRequest class,

        // we have the data in the request variable and we have to get them through it .
        Exercise exerciseCreated=new Exercise(request.name(),request.primaryMuscle(), request.listOfSecondaries());
        exerciseCreated=exerciseRepository.save(exerciseCreated); // we save the exercise in the DB

        // NOW WE HAVE received an exercise from the client ( Request) and we have saved it in the DB as exerciseCreated.
        // AND we have to return an exercise, we return what we get from the DB and not the raw input to ensure
        // that we have saved it correctly in the DB and we can retrieve it without problems

        ExerciseResponse savedExercise=   new ExerciseResponse(exerciseCreated.getId(),  exerciseCreated.getNameOfExercise(),
                exerciseCreated.getPrimaryMuscle(),exerciseCreated.getSecondaries() );
                return savedExercise;


    }






}
