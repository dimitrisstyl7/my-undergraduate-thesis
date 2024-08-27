package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.AppointmentDto;
import com.dimstyl.dietitianhub.entities.Appointment;
import com.dimstyl.dietitianhub.entities.UserInfo;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;
import com.dimstyl.dietitianhub.exceptions.AppointmentNotFoundException;
import com.dimstyl.dietitianhub.repositories.AppointmentRepository;
import com.dimstyl.dietitianhub.services.AppointmentService;
import com.dimstyl.dietitianhub.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserInfoService userInfoService;

    @Override
    public List<AppointmentDto> getAppointmentsByStatusAndScheduledDateTimeAfter(AppointmentStatus status,
                                                                                 LocalDateTime scheduledDateTime) {
        return appointmentRepository
                .findAllByStatusAndScheduledDateTimeIsAfter(status, scheduledDateTime).stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    @Transactional
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        UserInfo userInfo = userInfoService.getUserInfo(appointmentDto.clientId());
        Appointment appointment = appointmentDto.toAppointment(userInfo);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        return appointmentRepository.save(appointment).toDto();
    }

    @Override
    @Transactional
    public void updateAppointment(int id, AppointmentDto appointmentDto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));
        appointment.setTitle(appointmentDto.title());
        appointment.setDescription(appointmentDto.description());
    }

    @Override
    @Transactional
    public void deleteAnnouncement(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));
        appointmentRepository.delete(appointment);
    }

    @Override
    public boolean existsByScheduledDateTime(LocalDateTime scheduledDateTime) {
        return appointmentRepository.existsByScheduledDateTime(scheduledDateTime);
    }

}
