package com.dimstyl.dietitianhub.dtos;

import java.time.LocalDateTime;

public record AppointmentDto(int id, String title, String description, LocalDateTime start,
                             String formattedScheduledDateTime, String clientFullName) {
}
