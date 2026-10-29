package com.giannis.workout_tracker.dto;

import java.time.LocalDate;

/**
 *in this record we save the data that we SEND BACK to the client (phone) .
 *
 */
public record WorkoutResponse(Long id, int year, int month, int day ){



}
