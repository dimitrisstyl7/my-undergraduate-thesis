package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.api.ApiAppointmentDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebAppointmentDto;
import gr.unipi.thesis.dimstyl.entities.Appointment;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.enums.RequestType;
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
    public List<WebAppointmentDto> getAppointmentsByStatusesAfterGivenAppointmentDateTime(List<AppointmentStatus> statuses,
                                                                                          LocalDateTime dateTime) {
        return appointmentRepository
                .findAllByStatusInAndAppointmentDateTimeIsAfter(statuses, dateTime).stream()
                .map(Appointment::toWebDto)
                .toList();
    }

    @Override
    public List<WebAppointmentDto> getAppointmentsByStatusAfterGivenAppointmentDateTimeOrdered(AppointmentStatus status,
                                                                                               LocalDateTime dateTime) {
        return appointmentRepository
                .findAllByStatusAndAppointmentDateTimeIsAfterOrderByAppointmentDateTime(status, dateTime).stream()
                .map(Appointment::toWebDto)
                .toList();
    }

    @Override
    public List<WebAppointmentDto> getAppointmentsByStatusesWithinAppointmentDateTimeRange(List<AppointmentStatus> statuses,
                                                                                           LocalDateTime startDateTime,
                                                                                           LocalDateTime endDateTime) {
        return appointmentRepository
                .findAllByStatusInAndAppointmentDateTimeIsBetweenOrderByAppointmentDateTime(statuses, startDateTime, endDateTime)
                .stream()
                .map(Appointment::toWebDto)
                .toList();
    }

    @Override
    public List<WebAppointmentDto> getLatest5AppointmentsByStatusAfterGivenAppointmentDateTime(AppointmentStatus status,
                                                                                               LocalDateTime dateTime) {
        return appointmentRepository
                .findFirst5ByStatusAndAppointmentDateTimeIsAfterOrderByAppointmentDateTime(status, dateTime).stream()
                .map(Appointment::toWebDto)
                .toList();
    }

    @Override
    public List<WebAppointmentDto> getLatest5AppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository
                .findFirst5ByStatusOrderByAppointmentDateTimeDesc(status).stream()
                .map(Appointment::toWebDto)
                .toList();
    }

    @Override
    public List<ApiAppointmentDto> getLatest5AppointmentsByClientUsernameAndStatus(String username, AppointmentStatus status) {
        UserInfo userInfo = userInfoService.getUserInfo(username);
        return appointmentRepository.findFirst5ByClientUserInfoAndStatusOrderByAppointmentDateTimeAsc(userInfo, status)
                .stream()
                .map(Appointment::toApiDto)
                .toList();
    }

    @Override
    @Transactional
    public void createAppointment(WebAppointmentDto webAppointmentDto, RequestType requestType) {
        LocalDateTime appointmentDateTime = webAppointmentDto.start();
        UserInfo userInfo = userInfoService.getUserInfo(webAppointmentDto.clientId(), requestType);
        Optional<Appointment> appointmentOptional =
                appointmentRepository.findByAppointmentDateTimeAndStatusAndClientUserInfoId(
                        appointmentDateTime,
                        AppointmentStatus.PENDING,
                        userInfo.getId()
                );

        // If there is no appointment with the same scheduled date time and status PENDING, create a new appointment
        if (appointmentOptional.isEmpty()) {
            Appointment appointment = webAppointmentDto.toAppointment(userInfo);
            appointment.setStatus(AppointmentStatus.SCHEDULED);
            appointmentRepository.save(appointment);
        } else {
            // If there is an appointment with the same scheduled date time and status PENDING, update the appointment
            Appointment appointment = appointmentOptional.get();
            appointment.setTitle(webAppointmentDto.title());
            appointment.setDescription(webAppointmentDto.description());
            appointment.setStatus(AppointmentStatus.SCHEDULED);
        }
    }

    @Override
    @Transactional
    public void updateAppointment(int id, WebAppointmentDto webAppointmentDto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));
        appointment.setTitle(webAppointmentDto.title());
        appointment.setDescription(webAppointmentDto.description());
    }

    @Override
    @Transactional
    public void completeAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));
        if (appointment.getAppointmentDateTime().isAfter(LocalDateTime.now())) {
            throw new AppointmentIsInTheFutureException("Appointment with id %d is in the future".formatted(id));
        }
        appointment.setStatus(AppointmentStatus.COMPLETED);
    }

    @Override
    @Transactional
    public void cancelAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));
        if (appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
            throw new AppointmentIsInThePastException("Appointment with id %d is in the past".formatted(id));
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
    }

    @Override
    @Transactional
    public void approveAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));

        if (existsByAppointmentDateTimeAndStatus(appointment.getAppointmentDateTime(), AppointmentStatus.SCHEDULED)) {
            throw new AppointmentAlreadyExistsException("""
                    Appointment with the same scheduled date time already exists.
                    Pending appointment id: %d
                    Requested date time: %s
                    """.formatted(appointment.getId(), appointment.getAppointmentDateTime())
            );
        }

        appointment.setTitle(appointment.getClientUserInfo().getFullName());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.toWebDto();
    }

    @Override
    @Transactional
    public void declineAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id %d not found".formatted(id)));
        appointment.setStatus(AppointmentStatus.DECLINED);
    }

    @Override
    public boolean existsByAppointmentDateTimeAndStatus(LocalDateTime dateTime, AppointmentStatus status) {
        return appointmentRepository.existsByAppointmentDateTimeAndStatus(dateTime, status);
    }

    @Override
    @Transactional
    public void markAppointmentsWithStatusBeforeNowAsCompleted(AppointmentStatus status) {
        List<Appointment> appointments =
                appointmentRepository.findAllByStatusAndAppointmentDateTimeIsBefore(status, LocalDateTime.now());
        appointments.forEach(appointment -> appointment.setStatus(AppointmentStatus.COMPLETED));
    }

}
