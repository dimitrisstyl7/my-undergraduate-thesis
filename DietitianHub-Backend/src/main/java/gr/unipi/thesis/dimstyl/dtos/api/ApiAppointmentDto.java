package gr.unipi.thesis.dimstyl.dtos.api;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiAppointmentDto(int id, LocalDateTime appointmentDateTime, String formattedAppointmentDateTime) {
}
