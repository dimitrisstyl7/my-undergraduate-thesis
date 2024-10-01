package gr.unipi.thesis.dimstyl.entities;

import gr.unipi.thesis.dimstyl.dtos.web.WebClientDto;
import gr.unipi.thesis.dimstyl.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

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
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false, length = 102)
    private String username;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default false")
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "role", nullable = false, columnDefinition = "role_enum")
    private UserRole role;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private UserInfo userInfo;

    public WebClientDto toWebClientDto() {
        return WebClientDto.builder()
                .id(id)
                .phone(userInfo.getPhone())
                .email(userInfo.getEmail())
                .dateOfBirth(userInfo.getDateOfBirth())
                .gender(userInfo.getGender())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .build();
    }

}