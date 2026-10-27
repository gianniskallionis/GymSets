package com.giannis.workout_tracker.dto;

import java.time.LocalDate;

public record WorkoutResponse(Long id , LocalDate (int year, int month, int day) )    {
}
