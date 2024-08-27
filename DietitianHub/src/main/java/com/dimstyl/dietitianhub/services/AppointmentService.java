package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.AppointmentDto;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> getAppointmentsByStatusAndScheduledDateTimeAfter(AppointmentStatus status,
                                                                          LocalDateTime scheduledDateTime);

    AppointmentDto createAppointment(AppointmentDto appointmentDto);

    void updateAppointment(int id, AppointmentDto appointmentDto);

    boolean existsByScheduledDateTime(LocalDateTime scheduledDateTime);

}
