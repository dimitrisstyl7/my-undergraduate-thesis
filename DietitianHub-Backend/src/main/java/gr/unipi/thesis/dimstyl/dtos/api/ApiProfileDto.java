package gr.unipi.thesis.dimstyl.dtos.api;

import gr.unipi.thesis.dimstyl.enums.Gender;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ApiProfileDto(String firstName,
                            String lastName,
                            String email,
                            String phone,
                            Gender gender,
                            LocalDate dateOfBirth) {
}
