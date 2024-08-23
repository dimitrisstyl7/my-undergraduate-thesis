package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.AppointmentDto;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;

import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> getAppointmentsByStatus(AppointmentStatus status);

    void updateAppointment(int id, AppointmentDto appointmentDto);

}
