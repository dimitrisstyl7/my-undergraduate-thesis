package gr.unipi.thesis.dimstyl.repositories;

import gr.unipi.thesis.dimstyl.entities.Appointment;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAllByStatusInAndScheduledAtIsAfter(List<AppointmentStatus> statuses, LocalDateTime dateTime);

    List<Appointment> findAllByStatusAndScheduledAtIsAfterOrderByScheduledAt(AppointmentStatus status, LocalDateTime dateTime);

    List<Appointment> findAllByStatusAndScheduledAtIsBefore(AppointmentStatus status, LocalDateTime dateTime);

    List<Appointment> findAllByStatusInAndScheduledAtIsBetweenOrderByScheduledAt(List<AppointmentStatus> statuses,
                                                                                 LocalDateTime startDateTime,
                                                                                 LocalDateTime endDateTime);

    List<Appointment> findFirst5ByStatusAndScheduledAtIsAfterOrderByScheduledAt(AppointmentStatus status, LocalDateTime dateTime);

    List<Appointment> findFirst5ByStatusOrderByScheduledAtDesc(AppointmentStatus status);

    Optional<Appointment> findByScheduledAtAndStatusAndClientUserInfoId(LocalDateTime dateTime,
                                                                        AppointmentStatus status,
                                                                        int clientUserInfoId);

    boolean existsByScheduledAtAndStatus(LocalDateTime dateTime, AppointmentStatus status);

}
