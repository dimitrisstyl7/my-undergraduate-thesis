package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.AppointmentDto;
import com.dimstyl.dietitianhub.entities.Appointment;
import com.dimstyl.dietitianhub.entities.UserInfo;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;
import com.dimstyl.dietitianhub.exceptions.AppointmentAlreadyExistsException;
import com.dimstyl.dietitianhub.exceptions.AppointmentIsInTheFutureException;
import com.dimstyl.dietitianhub.exceptions.AppointmentIsInThePastException;
import com.dimstyl.dietitianhub.exceptions.AppointmentNotFoundException;
import com.dimstyl.dietitianhub.repositories.AppointmentRepository;
import com.dimstyl.dietitianhub.services.AppointmentService;
import com.dimstyl.dietitianhub.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserInfoService userInfoService;

    @Override
    public List<AppointmentDto> getAppointmentsByStatusAndScheduledDateTimeIsAfter(List<AppointmentStatus> statuses,
                                                                                   LocalDateTime dateTime) {
        return appointmentRepository
                .findAllByStatusInAndScheduledDateTimeIsAfter(statuses, dateTime).stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAppointmentsByStatusAndScheduledDateTimeIsAfterOrderByScheduledDateTime(AppointmentStatus status,
                                                                                                           LocalDateTime dateTime) {
        return appointmentRepository
                .findAllByStatusAndScheduledDateTimeIsAfterOrderByScheduledDateTime(status, dateTime).stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAppointmentsByStatusAndScheduledDateTimeIsBetween(List<AppointmentStatus> statuses,
                                                                                     LocalDateTime startDateTime,
                                                                                     LocalDateTime endDateTime) {
        return appointmentRepository
                .findAllByStatusInAndScheduledDateTimeIsBetweenOrderByScheduledDateTime(statuses, startDateTime, endDateTime)
                .stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getFirst5AppointmentsByStatusAndScheduledDateTimeIsAfter(AppointmentStatus status,
                                                                                         LocalDateTime dateTime) {
        return appointmentRepository
                .findFirst5ByStatusAndScheduledDateTimeIsAfterOrderByScheduledDateTime(status, dateTime).stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getFirst5AppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository
                .findFirst5ByStatusOrderByScheduledDateTimeDesc(status).stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void createAppointment(AppointmentDto appointmentDto) {
        LocalDateTime scheduledDateTime = appointmentDto.start();
        UserInfo userInfo = userInfoService.getUserInfo(appointmentDto.clientId());
        Optional<Appointment> appointmentOptional =
                appointmentRepository.findByScheduledDateTimeAndStatusAndClientUserInfoId(
                        scheduledDateTime,
                        AppointmentStatus.PENDING,
                        userInfo.getId()
                );

        // If there is no appointment with the same scheduled date time and status PENDING, create a new appointment.
        if (appointmentOptional.isEmpty()) {
            Appointment appointment = appointmentDto.toAppointment(userInfo);
            appointment.setStatus(AppointmentStatus.SCHEDULED);
            appointmentRepository.save(appointment);
        } else {
            // If there is an appointment with the same scheduled date time and status PENDING, update the appointment.
            Appointment appointment = appointmentOptional.get();
            appointment.setTitle(appointmentDto.title());
            appointment.setDescription(appointmentDto.description());
            appointment.setStatus(AppointmentStatus.SCHEDULED);
        }
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
    public void completeAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));
        if (appointment.getScheduledDateTime().isAfter(LocalDateTime.now())) {
            throw new AppointmentIsInTheFutureException("Appointment with id %d is in the future".formatted(id));
        }
        appointment.setStatus(AppointmentStatus.COMPLETED);
    }

    @Override
    @Transactional
    public void cancelAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));
        if (appointment.getScheduledDateTime().isBefore(LocalDateTime.now())) {
            throw new AppointmentIsInThePastException("Appointment with id %d is in the past".formatted(id));
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
    }

    @Override
    @Transactional
    public void approveAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));

        if (existsByScheduledDateTimeAndStatus(appointment.getScheduledDateTime(), AppointmentStatus.SCHEDULED)) {
            throw new AppointmentAlreadyExistsException("""
                    Appointment with the same scheduled date time already exists.
                    Pending appointment id: %d
                    Requested date time: %s
                    """.formatted(appointment.getId(), appointment.getScheduledDateTime())
            );
        }

        appointment.setTitle(appointment.getClientUserInfo().getFullName());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.toDto();
    }

    @Override
    @Transactional
    public void declineAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));
        appointment.setStatus(AppointmentStatus.DECLINED);
    }

    @Override
    public boolean existsByScheduledDateTimeAndStatus(LocalDateTime dateTime, AppointmentStatus status) {
        return appointmentRepository.existsByScheduledDateTimeAndStatus(dateTime, status);
    }

    @Override
    @Transactional
    public void markPastAppointmentsAsCompleted(AppointmentStatus status) {
        List<Appointment> appointments =
                appointmentRepository.findAllByStatusAndScheduledDateTimeIsBefore(status, LocalDateTime.now());
        appointments.forEach(appointment -> appointment.setStatus(AppointmentStatus.COMPLETED));
    }

}
