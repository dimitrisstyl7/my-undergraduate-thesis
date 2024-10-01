package gr.unipi.thesis.dimstyl.dtos.web;

import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.enums.Gender;
import gr.unipi.thesis.dimstyl.enums.UserRole;
import gr.unipi.thesis.dimstyl.utilities.DateTimeUtil;
import gr.unipi.thesis.dimstyl.utilities.RegistrationUtil;
import gr.unipi.thesis.dimstyl.validators.gender.GenderConstraint;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebClientDto {

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
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Email(message = "Invalid email address")
    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 8, max = 20, message = "Phone number must be between 8 and 20 digits (including '+')")
    @Pattern(regexp = "^[+]?[0-9]{8,19}$", message = "Phone number can start with '+' and be followed by 8-19 digits")
    private String phone;

    public String getFormattedDateOfBirth() {
        return DateTimeUtil.getFormattedDate(dateOfBirth);
    }

    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }

    public User toUserForRegistration(UserRole role) {
        String username = RegistrationUtil.generateUsername(firstName, lastName, dateOfBirth);
        String password = RegistrationUtil.generatePassword();

        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    public UserInfo toUserInfo(User user) {
        return UserInfo.builder()
                .user(user)
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .email(email)
                .phone(phone)
                .build();
    }

}
