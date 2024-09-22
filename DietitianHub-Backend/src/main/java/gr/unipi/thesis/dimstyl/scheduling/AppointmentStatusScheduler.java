package gr.unipi.thesis.dimstyl.scheduling;

import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentStatusScheduler {

    private final AppointmentService appointmentService;

    @Scheduled(cron = "@midnight")
    public void markScheduledAppointmentsBeforeNowAsCompleted() {
        appointmentService.markAppointmentsWithStatusBeforeNowAsCompleted(AppointmentStatus.SCHEDULED);
    }

}
