package gr.unipi.thesis.dimstyl.entities;

import gr.unipi.thesis.dimstyl.dtos.web.WebArticleDto;
import gr.unipi.thesis.dimstyl.utilities.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "article", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "article_tag_association",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"article_id", "tag_id"})
    )
    private List<Tag> tags;

    public WebArticleDto toWebDto() {
        List<String> tagNames = tags.stream().map(Tag::getName).toList();
        List<Integer> tagIds = tags.stream().map(Tag::getId).toList();
        String createdAt = DateTimeUtil.getFormattedDateTime(this.createdAt);
        return WebArticleDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .tagIds(tagIds)
                .tagNames(tagNames)
                .createdAt(createdAt)
                .build();
    }

}
