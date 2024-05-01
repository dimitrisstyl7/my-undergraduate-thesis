package com.dimstyl.dietitianhub.security;

import com.dimstyl.dietitianhub.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.dimstyl.dietitianhub.constants.Endpoints.*;
import static com.dimstyl.dietitianhub.enums.UserRole.DIETITIAN;

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
                                .anyRequest().hasAuthority(DIETITIAN.name())
                )
                .formLogin(form ->
                        form
                                .loginPage(LOGIN_ENDPOINT)
                                .loginProcessingUrl(AUTHENTICATE_USER_ENDPOINT)
                                .permitAll()
                )
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ENDPOINT)))
                .build();
    }
}