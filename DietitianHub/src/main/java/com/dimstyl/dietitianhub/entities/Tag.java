package com.dimstyl.dietitianhub.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tag", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tag_category_id", referencedColumnName = "id", nullable = false)
    private TagCategory category;

    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;
}
