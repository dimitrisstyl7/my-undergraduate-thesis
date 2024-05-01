package com.dimstyl.dietitianhub.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "user", schema = "public")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 80)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private UserInfo userInfo;
}