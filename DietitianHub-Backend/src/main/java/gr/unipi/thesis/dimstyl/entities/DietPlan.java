package gr.unipi.thesis.dimstyl.entities;

import gr.unipi.thesis.dimstyl.dtos.DietPlanDto;
import gr.unipi.thesis.dimstyl.utilities.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDate;


@Entity
@Table(
        name = "diet_plan",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_info_id", "created_on"})
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DietPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_info_id", referencedColumnName = "id", nullable = false)
    private UserInfo userInfo;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDate createdOn;

    public DietPlanDto toDto() {
        String createdOn = DateTimeUtil.getFormattedDate(this.createdOn);
        return DietPlanDto.builder()
                .id(id)
                .name(name)
                .createdOn(createdOn)
                .build();
    }

}
