package com.dimstyl.dietitianhub.dtos;

import com.dimstyl.dietitianhub.validators.gender.GenderConstraint;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private int id;

    @NotBlank(message = "First Name cannot be empty")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z]{2,50}$", message = "First Name must contain only letters")
    private String firstName;

    @NotBlank(message = "Last Name cannot be empty")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z]{2,50}$", message = "Last Name must contain only letters")
    private String lastName;

    @GenderConstraint(message = "Gender is required")
    private char gender;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Email(message = "Invalid email address.")
    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 8, max = 20, message = "Phone number must be between 8 and 20 digits")
    @Pattern(regexp = "^[0-9]{8,20}$", message = "Phone number must contain only digits")
    private String phone;

    public String getFormattedDateOfBirth() {
        return DateUtil.getFormattedDate(dateOfBirth);
    }

    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }

}
