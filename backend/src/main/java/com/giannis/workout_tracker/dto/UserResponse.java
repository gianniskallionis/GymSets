package com.giannis.workout_tracker.dto;

// the data we give back to the client, about authentication
public record UserResponse(Long id,String username) {
}
