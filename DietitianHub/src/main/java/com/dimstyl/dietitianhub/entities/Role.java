package com.dimstyl.dietitianhub.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "role", schema = "public")
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 9)
    private String name;
}