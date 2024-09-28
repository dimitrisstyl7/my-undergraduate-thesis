package gr.unipi.thesis.dimstyl.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank String username, @NotBlank String password) {
}
