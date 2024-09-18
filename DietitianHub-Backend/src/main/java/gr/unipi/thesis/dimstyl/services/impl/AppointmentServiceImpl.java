package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.AppointmentDto;
import gr.unipi.thesis.dimstyl.entities.Appointment;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.exceptions.appointment.AppointmentAlreadyExistsException;
import gr.unipi.thesis.dimstyl.exceptions.appointment.AppointmentIsInTheFutureException;
import gr.unipi.thesis.dimstyl.exceptions.appointment.AppointmentIsInThePastException;
import gr.unipi.thesis.dimstyl.exceptions.appointment.AppointmentNotFoundException;
import gr.unipi.thesis.dimstyl.repositories.AppointmentRepository;
import gr.unipi.thesis.dimstyl.services.AppointmentService;
import gr.unipi.thesis.dimstyl.services.UserInfoService;
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
    public List<AppointmentDto> getAppointmentsByStatusAndScheduledAtIsAfter(List<AppointmentStatus> statuses,
                                                                             LocalDateTime dateTime) {
        return appointmentRepository
                .findAllByStatusInAndScheduledAtIsAfter(statuses, dateTime).stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAppointmentsByStatusAndScheduledAtIsAfterOrderByScheduledAt(AppointmentStatus status,
                                                                                               LocalDateTime dateTime) {
        return appointmentRepository
                .findAllByStatusAndScheduledAtIsAfterOrderByScheduledAt(status, dateTime).stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAppointmentsByStatusAndScheduledAtIsBetween(List<AppointmentStatus> statuses,
                                                                               LocalDateTime startDateTime,
                                                                               LocalDateTime endDateTime) {
        return appointmentRepository
                .findAllByStatusInAndScheduledAtIsBetweenOrderByScheduledAt(statuses, startDateTime, endDateTime)
                .stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getFirst5AppointmentsByStatusAndScheduledAtIsAfter(AppointmentStatus status,
                                                                                   LocalDateTime dateTime) {
        return appointmentRepository
                .findFirst5ByStatusAndScheduledAtIsAfterOrderByScheduledAt(status, dateTime).stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getFirst5AppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository
                .findFirst5ByStatusOrderByScheduledAtDesc(status).stream()
                .map(Appointment::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void createAppointment(AppointmentDto appointmentDto) {
        LocalDateTime scheduledAt = appointmentDto.start();
        UserInfo userInfo = userInfoService.getUserInfo(appointmentDto.clientId());
        Optional<Appointment> appointmentOptional =
                appointmentRepository.findByScheduledAtAndStatusAndClientUserInfoId(
                        scheduledAt,
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
        if (appointment.getScheduledAt().isAfter(LocalDateTime.now())) {
            throw new AppointmentIsInTheFutureException("Appointment with id %d is in the future".formatted(id));
        }
        appointment.setStatus(AppointmentStatus.COMPLETED);
    }

    @Override
    @Transactional
    public void cancelAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));
        if (appointment.getScheduledAt().isBefore(LocalDateTime.now())) {
            throw new AppointmentIsInThePastException("Appointment with id %d is in the past".formatted(id));
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
    }

    @Override
    @Transactional
    public void approveAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));

        if (existsByScheduledAtAndStatus(appointment.getScheduledAt(), AppointmentStatus.SCHEDULED)) {
            throw new AppointmentAlreadyExistsException("""
                    Appointment with the same scheduled date time already exists.
                    Pending appointment id: %d
                    Requested date time: %s
                    """.formatted(appointment.getId(), appointment.getScheduledAt())
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
    public boolean existsByScheduledAtAndStatus(LocalDateTime dateTime, AppointmentStatus status) {
        return appointmentRepository.existsByScheduledAtAndStatus(dateTime, status);
    }

    @Override
    @Transactional
    public void markPastAppointmentsAsCompleted(AppointmentStatus status) {
        List<Appointment> appointments =
                appointmentRepository.findAllByStatusAndScheduledAtIsBefore(status, LocalDateTime.now());
        appointments.forEach(appointment -> appointment.setStatus(AppointmentStatus.COMPLETED));
    }

}
