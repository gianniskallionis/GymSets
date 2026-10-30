package com.giannis.workout_tracker.controller;


import com.giannis.workout_tracker.dto.*;
import com.giannis.workout_tracker.model.Exercise;
import com.giannis.workout_tracker.model.Workout;
import com.giannis.workout_tracker.model.WorkoutSet;
import com.giannis.workout_tracker.repository.ExerciseRepository;
import com.giannis.workout_tracker.repository.WorkoutRepository;
import com.giannis.workout_tracker.repository.WorkoutSetRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController       // all the returns inside the class goes to the client as Json ( through the api)
@RequestMapping("/workouts") // all methods in the class communicate with the api through the common /workouts
public class WorkoutController {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSetRepository workoutSetRepository;


    public WorkoutController(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository, WorkoutSetRepository workoutSetRepository) {
        this.workoutRepository=workoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutSetRepository = workoutSetRepository;
    }

    /**
     * it creates a workout,With data given from the client,  saves it in the DB and returns a validated object
     * @param request an object given from the client
     * @return the object back
     */
    @PostMapping               // requestBody turns the json input and turns them into java objects
    public WorkoutResponse createWorkout(@RequestBody CreateWorkoutRequest request ){

        Workout workoutCreated=new Workout(request.year(), request.month(), request.day()); // we create a new workout with the date
        workoutCreated=workoutRepository.save(workoutCreated); // we save it in the DB

        return new WorkoutResponse(workoutCreated.getId(), workoutCreated.getDate().getYear() ,workoutCreated.getDate().getMonthValue()
                , workoutCreated.getDate().getDayOfMonth());  // we return a workout object witht the id and the date

    }

    /**
     * it creates a workoutSet with data from the client and it saves it in the DB , then it returns  the same object with its id filled
     * @param request the object with data from the client
     * @return the object with data of the saved workoutSe
     */
    @PostMapping("/{id}/sets") // we take the id of the workout to use in the set
    public WorkoutSetResponse createWorkoutSet(@PathVariable Long id, @RequestBody CreateWorkoutSetRequest request ){
        //                  pathVariable takes whatever is in the {id} from the postmapping and puts it in the id

        Workout workoutOfTheSet=workoutRepository.findById(id).orElseThrow(); // the workout of the Set

        //                                   a workoutSet has ( Exercise, KG, reps )
        WorkoutSet workoutsetCreated =new WorkoutSet(  exerciseRepository.findById(request.exerciseId()).orElseThrow(),request.kg(),request.reps());
        // in the repository classes through the extends we have methods to find each entity( to find an exercise you need
        // an exerciseRepository object, and you can find the exercise by id and other methods are available.
        workoutOfTheSet.addSet(workoutsetCreated);  // we add the set we created to its workout
        workoutsetCreated= workoutSetRepository.save(workoutsetCreated);

        WorkoutSetResponse workoutSetSaved= new WorkoutSetResponse(workoutsetCreated.getId(),
                (        (workoutsetCreated.getExercise() ).getId()   ),
                //              (    exercise  ).id
                workoutsetCreated.getKg() ,  workoutsetCreated.getReps());
        return workoutSetSaved;


    }

    /**
     * the method where we find the volume of the workout  given the id
     * @param id the id of the workout
     * @return the volume for all the muscles involved
     */
    @GetMapping("/{id}/volume")
       public Map<Exercise.Muscles,Double> getVolume(@PathVariable Long id ){
        Workout currentWorkout= workoutRepository.findById(id).orElseThrow();
        // we make find the Workout  object from the id given as a parameter.
            return currentWorkout.volume();
    }


    @GetMapping
    public List <WorkoutResponse> getAllWorkouts(){
        List <WorkoutResponse> listOfWorkouts = new ArrayList<>();
        for (Workout W:workoutRepository.findAll()) { // for every workout in the repository

            WorkoutResponse Wo=new WorkoutResponse(W.getId() ,W.getDate().getYear(),W.getDate().getMonthValue(),W.getDate().getDayOfMonth()) ;
            // we make a Workout response out of the Workout so we can  receive it
            listOfWorkouts.add(Wo);
        }
        return listOfWorkouts;




    }




}
