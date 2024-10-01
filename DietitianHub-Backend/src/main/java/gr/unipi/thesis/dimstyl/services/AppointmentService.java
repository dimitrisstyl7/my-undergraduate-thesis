package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.api.ApiAppointmentDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebAppointmentDto;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.enums.RequestType;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    List<WebAppointmentDto> getAppointmentsByStatusesAfterGivenAppointmentDateTime(List<AppointmentStatus> statuses, LocalDateTime dateTime);

    List<WebAppointmentDto> getAppointmentsByStatusAfterGivenAppointmentDateTimeOrdered(AppointmentStatus status, LocalDateTime dateTime);

    List<WebAppointmentDto> getAppointmentsByStatusesWithinAppointmentDateTimeRange(List<AppointmentStatus> statuses,
                                                                                    LocalDateTime startDateTime,
                                                                                    LocalDateTime endDateTime);

    List<WebAppointmentDto> getLatest5AppointmentsByStatusAfterGivenAppointmentDateTime(AppointmentStatus status, LocalDateTime dateTime);

    List<WebAppointmentDto> getLatest5AppointmentsByStatus(AppointmentStatus status);

    List<ApiAppointmentDto> getLatest5AppointmentsByClientUsernameAndStatus(String username, AppointmentStatus status);

    void createAppointment(WebAppointmentDto webAppointmentDto, RequestType requestType);

    void updateAppointment(int id, WebAppointmentDto webAppointmentDto);

    void completeAppointment(int id);

    void cancelAppointment(int id);

    void approveAppointment(int id);

    void declineAppointment(int id);

    boolean existsByAppointmentDateTimeAndStatus(LocalDateTime dateTime, AppointmentStatus status);

    void markAppointmentsWithStatusBeforeNowAsCompleted(AppointmentStatus status);

}
