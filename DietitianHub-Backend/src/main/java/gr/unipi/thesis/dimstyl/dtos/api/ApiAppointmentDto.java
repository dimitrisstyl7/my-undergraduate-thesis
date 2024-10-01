package gr.unipi.thesis.dimstyl.dtos.api;

import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import lombok.Builder;

@Builder
public record ApiAppointmentDto(int id, String start, AppointmentStatus status) {
}
