package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.AppointmentDto;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> getAppointmentsByStatusAndScheduledDateTimeIsAfter(List<AppointmentStatus> statuses,
                                                                            LocalDateTime dateTime);

    List<AppointmentDto> getAppointmentsByStatusAndScheduledDateTimeIsAfterOrderByScheduledDateTime(AppointmentStatus status,
                                                                                                    LocalDateTime dateTime);

    List<AppointmentDto> getAppointmentsByStatusAndScheduledDateTimeIsBetween(List<AppointmentStatus> statuses,
                                                                              LocalDateTime startDateTime,
                                                                              LocalDateTime endDateTime);

    List<AppointmentDto> getFirst5AppointmentsByStatusAndScheduledDateTimeIsAfter(AppointmentStatus status,
                                                                                  LocalDateTime dateTime);

    List<AppointmentDto> getFirst5AppointmentsByStatus(AppointmentStatus status);

    void createAppointment(AppointmentDto appointmentDto);

    void updateAppointment(int id, AppointmentDto appointmentDto);

    void completeAppointment(int id);

    void cancelAppointment(int id);

    void approveAppointment(int id);

    void declineAppointment(int id);

    boolean existsByScheduledDateTimeAndStatus(LocalDateTime dateTime, AppointmentStatus status);

    void markPastAppointmentsAsCompleted(AppointmentStatus status);

}
