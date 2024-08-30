package com.dimstyl.dietitianhub.repositories;

import com.dimstyl.dietitianhub.entities.Appointment;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAllByStatusInAndScheduledDateTimeIsAfter(List<AppointmentStatus> statuses,
                                                                   LocalDateTime dateTime);

    List<Appointment> findAllByStatusAndScheduledDateTimeIsAfterOrderByScheduledDateTime(AppointmentStatus status,
                                                                                         LocalDateTime dateTime);

    List<Appointment> findAllByStatusAndScheduledDateTimeIsBefore(AppointmentStatus status, LocalDateTime dateTime);

    List<Appointment> findAllByStatusInAndScheduledDateTimeIsBetweenOrderByScheduledDateTime(List<AppointmentStatus> statuses,
                                                                                             LocalDateTime startDateTime,
                                                                                             LocalDateTime endDateTime);

    List<Appointment> findFirst5ByStatusAndScheduledDateTimeIsAfterOrderByScheduledDateTime(AppointmentStatus status,
                                                                                            LocalDateTime dateTime);

    List<Appointment> findFirst5ByStatusOrderByScheduledDateTimeDesc(AppointmentStatus status);

    Optional<Appointment> findByScheduledDateTimeAndStatusAndClientUserInfoId(LocalDateTime dateTime,
                                                                              AppointmentStatus status,
                                                                              int clientUserInfoId);

    boolean existsByScheduledDateTimeAndStatus(LocalDateTime dateTime, AppointmentStatus status);

}
