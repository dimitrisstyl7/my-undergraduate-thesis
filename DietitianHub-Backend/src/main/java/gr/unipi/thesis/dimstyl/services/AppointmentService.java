package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.AppointmentDto;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> getAppointmentsByStatusAndScheduledAtIsAfter(List<AppointmentStatus> statuses, LocalDateTime dateTime);

    List<AppointmentDto> getAppointmentsByStatusAndScheduledAtIsAfterOrderByScheduledAt(AppointmentStatus status, LocalDateTime dateTime);

    List<AppointmentDto> getAppointmentsByStatusAndScheduledAtIsBetween(List<AppointmentStatus> statuses,
                                                                        LocalDateTime startDateTime,
                                                                        LocalDateTime endDateTime);

    List<AppointmentDto> getFirst5AppointmentsByStatusAndScheduledAtIsAfter(AppointmentStatus status, LocalDateTime dateTime);

    List<AppointmentDto> getFirst5AppointmentsByStatus(AppointmentStatus status);

    void createAppointment(AppointmentDto appointmentDto);

    void updateAppointment(int id, AppointmentDto appointmentDto);

    void completeAppointment(int id);

    void cancelAppointment(int id);

    void approveAppointment(int id);

    void declineAppointment(int id);

    boolean existsByScheduledAtAndStatus(LocalDateTime dateTime, AppointmentStatus status);

    void markPastAppointmentsAsCompleted(AppointmentStatus status);

}
