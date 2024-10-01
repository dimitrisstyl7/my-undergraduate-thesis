package gr.unipi.thesis.dimstyl.dtos.api;

import lombok.Builder;

import java.util.List;

@Builder
public record AppointmentsResponse(List<ApiAppointmentDto> scheduledAppointments,
                                   List<ApiAppointmentDto> pendingAppointments,
                                   List<ApiAppointmentDto> completedAppointments,
                                   List<ApiAppointmentDto> declinedAppointments,
                                   List<ApiAppointmentDto> cancelledAppointments) {
}
