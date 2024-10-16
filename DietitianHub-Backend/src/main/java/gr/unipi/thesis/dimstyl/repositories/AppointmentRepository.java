package gr.unipi.thesis.dimstyl.repositories;

import gr.unipi.thesis.dimstyl.entities.Appointment;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAllByStatusInAndAppointmentDateTimeIsAfter(List<AppointmentStatus> statuses, LocalDateTime dateTime);

    List<Appointment> findAllByStatusAndAppointmentDateTimeIsAfterOrderByAppointmentDateTime(AppointmentStatus status,
                                                                                             LocalDateTime dateTime);

    List<Appointment> findAllByStatusAndAppointmentDateTimeIsBefore(AppointmentStatus status, LocalDateTime dateTime);

    List<Appointment> findAllByStatusInAndAppointmentDateTimeIsBetweenOrderByAppointmentDateTime(List<AppointmentStatus> statuses,
                                                                                                 LocalDateTime startDateTime,
                                                                                                 LocalDateTime endDateTime);

    List<Appointment> findFirst5ByStatusAndAppointmentDateTimeIsAfterOrderByAppointmentDateTime(AppointmentStatus status,
                                                                                                LocalDateTime dateTime);

    List<Appointment> findFirst5ByStatusOrderByAppointmentDateTimeDesc(AppointmentStatus status);

    List<Appointment> findFirst5ByClientUserInfo_IdAndStatusAndAppointmentDateTimeIsAfterOrderByAppointmentDateTime(int userInfoId,
                                                                                                                    AppointmentStatus status,
                                                                                                                    LocalDateTime dateTime);

    List<Appointment> findFirst5ByClientUserInfo_IdAndStatusOrderByAppointmentDateTimeDesc(int userInfoId, AppointmentStatus status);

    Optional<Appointment> findByAppointmentDateTimeAndStatusAndClientUserInfoId(LocalDateTime dateTime,
                                                                                AppointmentStatus status,
                                                                                int userInfoId);

    boolean existsByAppointmentDateTimeAndStatus(LocalDateTime dateTime, AppointmentStatus status);

    boolean existsByClientUserInfo_IdAndAppointmentDateTimeAndStatusIn(int userInfoId,
                                                                       LocalDateTime dateTime,
                                                                       List<AppointmentStatus> statuses);

}
