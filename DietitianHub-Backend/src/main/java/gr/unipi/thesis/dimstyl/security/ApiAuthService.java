package gr.unipi.thesis.dimstyl.security;

import gr.unipi.thesis.dimstyl.dtos.api.AuthenticationRequest;
import gr.unipi.thesis.dimstyl.dtos.api.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        CustomUserDetails user = ((CustomUserDetails) auth.getPrincipal());
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

}
