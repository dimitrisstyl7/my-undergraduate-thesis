package com.dimstyl.dietitianhub.entities;

import com.dimstyl.dietitianhub.dtos.AppointmentDto;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;
import com.dimstyl.dietitianhub.utilities.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "appointment",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = {"client_user_info_id", "scheduled_datetime"})
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_user_info_id", referencedColumnName = "id", nullable = false)
    private UserInfo clientUserInfo;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "scheduled_datetime", nullable = false)
    private LocalDateTime scheduledDateTime;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "status", nullable = false, columnDefinition = "appointment_status_enum")
    private AppointmentStatus status;

    public AppointmentDto toDto() {
        String formattedScheduledDateTime = DateTimeUtil.getFormattedDateTime(scheduledDateTime);
        String clientFullName = clientUserInfo.getFullName();
        return new AppointmentDto(id, title, description, scheduledDateTime, formattedScheduledDateTime, clientFullName);
    }

}
