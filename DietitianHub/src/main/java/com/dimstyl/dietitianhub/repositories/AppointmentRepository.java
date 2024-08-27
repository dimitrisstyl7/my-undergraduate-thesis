package com.dimstyl.dietitianhub.repositories;

import com.dimstyl.dietitianhub.entities.Appointment;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAllByStatusAndScheduledDateTimeIsAfter(AppointmentStatus status,
                                                                 LocalDateTime scheduledDateTime);

    boolean existsByScheduledDateTime(LocalDateTime scheduledDateTime);

}
