package com.dimstyl.dietitianhub.dtos;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Builder
@Getter
public class ClientDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 50)
    private String lastName;

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
