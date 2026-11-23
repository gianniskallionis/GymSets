package com.giannis.workout_tracker.controller;


import com.giannis.workout_tracker.dto.ExerciseResponse;
import com.giannis.workout_tracker.dto.CreateExerciseRequest;
import com.giannis.workout_tracker.model.Exercise;
import com.giannis.workout_tracker.model.Workout;
import com.giannis.workout_tracker.repository.ExerciseRepository;
import com.giannis.workout_tracker.repository.UserRepository;
import com.giannis.workout_tracker.repository.WorkoutSetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.giannis.workout_tracker.model.User;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController // the return goes back as Json, to the client
@RequestMapping("/exercises") // the methods of the class share the common /Exercise in the link
public class    ExerciseController {

    private final ExerciseRepository exerciseRepository;  // the repository we save and take from the data we use
    private final UserRepository userRepository;
    private final WorkoutSetRepository workoutSetRepository;

    public ExerciseController(ExerciseRepository exerciseRepository, UserRepository userRepository, WorkoutSetRepository workoutSetRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository=userRepository;
        this.workoutSetRepository=workoutSetRepository;
    }

    @PostMapping
    public ExerciseResponse createExercise(@RequestBody CreateExerciseRequest request, Authentication authentication){
         // the request body turns the incoming data from JSON form to java objects
        // we get the data from the phone  (client)  through the createExerciseRequest class,


        User userOfExercise=userRepository.findByUsername(authentication.getName())
                .orElseThrow(()->new IllegalArgumentException("user with this username does not exist"))  ;

        // we have the data in the request variable and we have to get them through it .
        Exercise exerciseCreated=new Exercise(request.name(),request.primaryMuscle(), request.listOfSecondaries());
        exerciseCreated.setUser(userOfExercise); // we set the user BEFORE SAVING( EVERY EXERCISE MUST HAVE A USER )
        exerciseCreated=exerciseRepository.save(exerciseCreated); // we save the exercise in the DB



        // NOW WE HAVE received an exercise from the client ( Request) and we have saved it in the DB as exerciseCreated.
        // AND we have to return an exercise, we return what we get from the DB and not the raw input to ensure
        // that we have saved it correctly in the DB and we can retrieve it without problems

        ExerciseResponse savedExercise=   new ExerciseResponse(exerciseCreated.getId(),  exerciseCreated.getNameOfExercise(),
                exerciseCreated.getPrimaryMuscle(),exerciseCreated.getSecondaries() );
                return savedExercise;


    }

    /**
     * The client Gets a list of all the exercises.
     * since the client needs them, we have to present them in the form : exerciseResponse
     * @return the list of exercises in the correct form
     */
    @GetMapping
    public List <ExerciseResponse>  getAllExercises(Authentication authentication){
    List <ExerciseResponse> listOfExercises = new ArrayList<>();
    for (Exercise E:exerciseRepository.findByUserUsername(authentication.getName())) { // for every exercise in the repository

        ExerciseResponse Ex=new ExerciseResponse(E.getId() , E.getNameOfExercise() , E.getPrimaryMuscle(), E.getSecondaries()) ;
        // we make an Exercise reponse out of the exercise so we can  receive it
        listOfExercises.add(Ex);
    }
        return listOfExercises;

    }


    @DeleteMapping("/{id}")
    public void deleteExercise(@PathVariable Long id,Authentication authentication ){
        Exercise exercise=exerciseRepository.findByIdAndUserUsername(id, authentication.getName()).
                orElseThrow(()->new NoSuchElementException("Exercise "+ id +" not found"));
        if( workoutSetRepository.existsByExerciseId(id)   ){
        throw new ResponseStatusException(HttpStatus.CONFLICT,"the exercise is used in a set");
        }
        else{ exerciseRepository.delete(exercise);}

    }




    }






