package com.dimstyl.dietitianhub.security;

import com.dimstyl.dietitianhub.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static com.dimstyl.dietitianhub.constants.Endpoints.*;
import static com.dimstyl.dietitianhub.constants.UserRoles.CLIENT_ROLE;
import static com.dimstyl.dietitianhub.constants.UserRoles.DIETITIAN_ROLE;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(
                                        "/css/**", "/data/**", "/fonts/**",
                                        "/images/**", "/js/**", "/libs/**"
                                ).permitAll()
                                .anyRequest().hasAnyAuthority(DIETITIAN_ROLE, CLIENT_ROLE)
                )
                .formLogin(form ->
                        form
                                .loginPage(LOGIN_ENDPOINT)
                                .loginProcessingUrl(AUTHENTICATE_USER_ENDPOINT)
                                .successHandler(this::customLoginSuccessHandler)
                                .permitAll()
                )
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ENDPOINT)))
                .build();
    }

    public void customLoginSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        System.out.println("HANDLER EXECUTION");
        if (authentication instanceof AnonymousAuthenticationToken) {
            System.out.println("HANDLER EXECUTION 2");
            System.out.println(authentication.isAuthenticated());
            return;
        }
        System.out.println("HANDLER EXECUTION 3");
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        System.out.println(role);
        switch (role) {
            case CLIENT_ROLE -> {
                authentication.setAuthenticated(false);
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            case DIETITIAN_ROLE -> {
                System.out.println("DIETITIAN");
                response.sendRedirect(INDEX_ENDPOINT);
            }
            default -> {
                // TODO
            }
        }
    }
}