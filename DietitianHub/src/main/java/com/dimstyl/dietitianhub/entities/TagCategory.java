package com.dimstyl.dietitianhub.entities;

import com.dimstyl.dietitianhub.dtos.TagCategoryDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tag_category", schema = "public")
@Getter
@Setter
public class TagCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Tag> tags;

    public TagCategoryDto toDto() {
        return new TagCategoryDto(name, tags.stream().map(Tag::toDto).toList());
    }

}
