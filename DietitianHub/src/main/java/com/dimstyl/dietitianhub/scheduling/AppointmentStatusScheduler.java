package com.dimstyl.dietitianhub.scheduling;

import com.dimstyl.dietitianhub.enums.AppointmentStatus;
import com.dimstyl.dietitianhub.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentStatusScheduler {

    private final AppointmentService appointmentService;

    @Scheduled(cron = "@midnight")
    public void updatePastAppointmentsStatus() {
        appointmentService.markPastAppointmentsAsCompleted(AppointmentStatus.SCHEDULED);
    }

}
