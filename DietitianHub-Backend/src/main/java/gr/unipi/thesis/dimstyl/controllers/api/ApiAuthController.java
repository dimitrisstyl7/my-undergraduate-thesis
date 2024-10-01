package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.api.AuthenticationRequest;
import gr.unipi.thesis.dimstyl.dtos.api.AuthenticationResponse;
import gr.unipi.thesis.dimstyl.security.ApiAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    private final ApiAuthService apiAuthService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request,
                                                               BindingResult result) {
        if (result.hasErrors()) throw new BadCredentialsException("Username and password are required.");
        return ResponseEntity.ok(apiAuthService.authenticate(request));
    }

}
