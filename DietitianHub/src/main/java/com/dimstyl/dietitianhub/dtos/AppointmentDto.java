package com.dimstyl.dietitianhub.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AppointmentDto(int id,
                             @NotBlank(message = "Title cannot be empty")
                             @Size(max = 100, message = "Title must be less than 100 characters")
                             String title,
                             String description,
                             LocalDateTime start,
                             String formattedScheduledDateTime,
                             String clientFullName) {
}
