package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.AppointmentDto;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.enums.RequestType;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> getAppointmentsByStatusesAfterGivenAppointmentDateTime(List<AppointmentStatus> statuses, LocalDateTime dateTime);

    List<AppointmentDto> getAppointmentsByStatusAfterGivenAppointmentDateTimeOrdered(AppointmentStatus status, LocalDateTime dateTime);

    List<AppointmentDto> getAppointmentsByStatusesWithinAppointmentDateTimeRange(List<AppointmentStatus> statuses,
                                                                                 LocalDateTime startDateTime,
                                                                                 LocalDateTime endDateTime);

    List<AppointmentDto> getLatest5AppointmentsByStatusAfterGivenAppointmentDateTime(AppointmentStatus status, LocalDateTime dateTime);

    List<AppointmentDto> getLatest5AppointmentsByStatus(AppointmentStatus status);

    void createAppointment(AppointmentDto appointmentDto, RequestType requestType);

    void updateAppointment(int id, AppointmentDto appointmentDto);

    void completeAppointment(int id);

    void cancelAppointment(int id);

    void approveAppointment(int id);

    void declineAppointment(int id);

    boolean existsByAppointmentDateTimeAndStatus(LocalDateTime dateTime, AppointmentStatus status);

    void markAppointmentsWithStatusBeforeNowAsCompleted(AppointmentStatus status);

}
