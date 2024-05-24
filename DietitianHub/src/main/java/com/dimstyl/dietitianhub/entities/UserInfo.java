package com.dimstyl.dietitianhub.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "user_info", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "gender", nullable = false)
    private char gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
}