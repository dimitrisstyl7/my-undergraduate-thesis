package gr.unipi.thesis.dimstyl.entities;

import gr.unipi.thesis.dimstyl.dtos.web.WebAnnouncementDto;
import gr.unipi.thesis.dimstyl.utilities.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;


@Entity
@Table(name = "announcement", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Integer id;

    @Column(name = "title", unique = true, nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public WebAnnouncementDto toWebDto() {
        String createdAt = DateTimeUtil.getFormattedDateTime(this.createdAt);
        return WebAnnouncementDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();
    }

}
