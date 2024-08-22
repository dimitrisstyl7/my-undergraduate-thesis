package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.AppointmentDto;
import com.dimstyl.dietitianhub.entities.Appointment;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;
import com.dimstyl.dietitianhub.repositories.AppointmentRepository;
import com.dimstyl.dietitianhub.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<AppointmentDto> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findAllByStatus(status).stream()
                .map(Appointment::toDto)
                .toList();
    }

}
