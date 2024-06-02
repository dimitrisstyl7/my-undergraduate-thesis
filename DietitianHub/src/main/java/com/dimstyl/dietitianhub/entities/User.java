package com.dimstyl.dietitianhub.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false, length = 102)
    private String username;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default false")
    private boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private UserInfo userInfo;

}