package gr.unipi.thesis.dimstyl.entities;

import gr.unipi.thesis.dimstyl.dtos.AppointmentDto;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.utilities.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;


@Entity
@Table(name = "appointment", schema = "public")
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

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "status", nullable = false, columnDefinition = "appointment_status_enum")
    private AppointmentStatus status;

    public AppointmentDto toDto() {
        String formattedScheduledAt = DateTimeUtil.getFormattedDateTime(scheduledAt);
        String clientFullName = clientUserInfo.getFullName();
        int clientId = clientUserInfo.getUser().getId();
        return AppointmentDto.builder()
                .id(id)
                .title(title)
                .description(description)
                .start(scheduledAt)
                .status(status)
                .formattedScheduledAt(formattedScheduledAt)
                .clientId(clientId)
                .clientFullName(clientFullName)
                .build();
    }

}
