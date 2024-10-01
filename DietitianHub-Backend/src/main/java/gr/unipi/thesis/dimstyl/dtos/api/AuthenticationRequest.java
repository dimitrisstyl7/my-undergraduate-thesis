package gr.unipi.thesis.dimstyl.dtos.api;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank String username, @NotBlank String password) {
}
