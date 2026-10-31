package com.giannis.workout_tracker.dto;

// the data that we Receive from the client , about the authentication
public record RegisterRequest(String username,String password) {
}
