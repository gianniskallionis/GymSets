package com.giannis.workout_tracker.controller;


import com.giannis.workout_tracker.dto.*;
import com.giannis.workout_tracker.model.Exercise;
import com.giannis.workout_tracker.model.User;
import com.giannis.workout_tracker.model.Workout;
import com.giannis.workout_tracker.model.WorkoutSet;
import com.giannis.workout_tracker.repository.ExerciseRepository;
import com.giannis.workout_tracker.repository.UserRepository;
import com.giannis.workout_tracker.repository.WorkoutRepository;
import com.giannis.workout_tracker.repository.WorkoutSetRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController       // all the returns inside the class goes to the client as Json ( through the api)
@RequestMapping("/workouts") // all methods in the class communicate with the api through the common /workouts
public class WorkoutController {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSetRepository workoutSetRepository;
    private final UserRepository userRepository;


    public WorkoutController(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository, WorkoutSetRepository workoutSetRepository, UserRepository userRepository) {
        this.workoutRepository=workoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutSetRepository = workoutSetRepository;
        this.userRepository = userRepository;
    }

    /**
     * it creates a workout,With data given from the client,  saves it in the DB and returns a validated object
     * @param request an object given from the client
     * @return the object back
     */
    @PostMapping               // requestBody turns the json input and turns them into java objects
    public WorkoutResponse createWorkout(@RequestBody CreateWorkoutRequest request, Authentication authentication){
//                                                  we need the authentication parameter to find out who sent the request
        User userOfWorkout=userRepository.findByUsername(authentication.getName())
                .orElseThrow(()->new IllegalArgumentException("user with this username does not exist"))  ;



        Workout workoutCreated=new Workout(request.year(), request.month(), request.day()); // we create a new workout with the date
        workoutCreated.setUser(userOfWorkout);// we save the user into the workout before we save it in the DB
        workoutCreated=workoutRepository.save(workoutCreated); // we save it in the DB

        return new WorkoutResponse(workoutCreated.getId(), workoutCreated.getDate().getYear() ,workoutCreated.getDate().getMonthValue()
                , workoutCreated.getDate().getDayOfMonth());  // we return a workout object witht the id and the date



    }

    /**
     * it creates a workoutSet with data from the client, and it saves it in the DB , then it returns  the same object with its id filled
     * @param request the object with data from the client
     * @return the object with data of the saved workoutSe
     */
    @PostMapping("/{id}/sets") // we take the id of the workout to use in the set
    public WorkoutSetResponse createWorkoutSet(@PathVariable Long id, @RequestBody CreateWorkoutSetRequest request,Authentication authentication ){
        //                  pathVariable takes whatever is in the {id} from the postmapping and puts it in the id

        Workout workoutOfTheSet= returnWorkout(id, authentication.getName());
        // we find the workout from the id  of the workout and the name of the user from the token

        //                                   a workoutSet has ( Exercise, KG, reps )
        WorkoutSet workoutsetCreated =new WorkoutSet(  exerciseRepository.findById(request.exerciseId()).
                orElseThrow(()->new NoSuchElementException("Exercise "+ request.exerciseId() +" not found"))
                ,request.kg(),request.reps());
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
       public Map<Exercise.Muscles,Double> getVolume(@PathVariable Long id,Authentication authentication ){
        // we  find the Workout  object from the id given as a parameter.  If not found, throws exception with message
       return returnWorkout(id,authentication.getName())     .volume();

    }

    @GetMapping ("/{id}/sets")
    public List<WorkoutSetResponse> getSetsOfWorkout (@PathVariable Long id,Authentication authentication ){
        List <WorkoutSetResponse> listOfSets= new ArrayList<>(); /*we create an empty list that we will put Sets in */
        for(WorkoutSet S:   returnWorkout(id, authentication.getName()).getSets()  )    { /*WE get the workout of the id and we get every set of it  */
                WorkoutSetResponse sr=new WorkoutSetResponse(S.getId(),S.getExercise().getId(),S.getKg(),S.getReps());
                listOfSets.add(sr);
        }
            return listOfSets;
    }







    @GetMapping
    public List <WorkoutResponse> getAllWorkouts(Authentication authentication){
        List <WorkoutResponse> listOfWorkouts = new ArrayList<>();
        for (Workout W: workoutRepository.findByUserUsername(authentication.getName())) { // for every workout in the repository

            WorkoutResponse Wo=new WorkoutResponse(W.getId() ,W.getDate().getYear(),W.getDate().getMonthValue(),W.getDate().getDayOfMonth()) ;
            // we make a Workout response out of the Workout so we can  receive it
            listOfWorkouts.add(Wo);
        }
        return listOfWorkouts;

    }

    /**
     * we return the workout if it's the righteous owner that requests it
     * @param id the id of the workout
     * @param username the username of the user
     * @return the workout if the correctu user asks it , otherwise we throw an exception
     */
    private Workout returnWorkout(Long id, String username){//id is  the id of the workout
        Workout currentWorkout= workoutRepository.findById(id).orElseThrow(()->new NoSuchElementException("workout "+id+ " not found"));
        // we find the workout from its id, if it doesn't exist we throw
        User currentUser=  currentWorkout.getUser();
        if (currentUser.getUsername().equals(username)){ // if the username of the user ( of the workout )
            // matches with the username given as the parameter, we return the workout
            return currentWorkout;
        }
        throw new NoSuchElementException(("workout "+id+ " not found"));
        // if the username is not the one matching the username of the user of the workout
        }

    }





