package gr.unipi.thesis.dimstyl.dtos.web;

import gr.unipi.thesis.dimstyl.entities.Appointment;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.validators.dateTime.Unique;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Builder
public record WebAppointmentDto(int id,
                                @NotBlank(message = "Title cannot be empty")
                             @Size(max = 100, message = "Title must be less than 100 characters")
                             String title,
                                String description,
                                @NotNull(message = "Date and Time cannot be empty")
                             @Future(message = "Date and Time must be in the future")
                             @Unique
                             LocalDateTime start,
                             /* Using `start` instead of `appointmentDateTime` as name
                             / to match the name in the `FullCalendar` library. */
                             AppointmentStatus status,
                                String formattedAppointmentDateTime,
                                @NotNull(message = "Please select a client")
                             @Range(min = 1, message = "Please select a client")
                             Integer clientId,
                                String clientFullName) {

    public Appointment toAppointment(UserInfo userInfo) {
        return Appointment.builder()
                .title(title)
                .description(description)
                .appointmentDateTime(start)
                .clientUserInfo(userInfo)
                .build();
    }

}
