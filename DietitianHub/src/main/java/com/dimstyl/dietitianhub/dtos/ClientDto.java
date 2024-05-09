package com.dimstyl.dietitianhub.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 50)
    private String lastName;

    private char gender;

    @NotNull
    @Past
    private Date dateOfBirth;

    @Email
    @Size(min = 1, max = 50)
    private String email;

    @NotBlank
    @Size(min = 1, max = 20)
    private String phone;
}
