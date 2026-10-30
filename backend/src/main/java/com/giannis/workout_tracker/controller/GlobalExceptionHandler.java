package com.giannis.workout_tracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.util.NoSuchElementException;

/**
 * GlobalExceptionHandler
 *
 */
 @RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     *
     * @param ex the NoSuchElement Exception that was thrown
     * @return
     */
    //Whenever any controller throws a NoSuchElementException, we handle it with this method
    @ExceptionHandler(NoSuchElementException.class)

    ResponseEntity<String> NotFoundHandler(NoSuchElementException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
                //  The response towards the client will have status Not_Found==404.
    }           // and the message of the response is given with the body(ex.getMessage() )


    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class, DateTimeException.class})
    ResponseEntity<String> IllegalAndNullHandler(RuntimeException ex){ // all 3 of the parameters are a kind of Runtime Exception
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());


    }





    public GlobalExceptionHandler() {
    }


}
