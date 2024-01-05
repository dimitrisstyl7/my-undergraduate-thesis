package com.dimstyl.dietitianhub.security;

import com.dimstyl.dietitianhub.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.dimstyl.dietitianhub.constants.Endpoints.LOGIN_ENDPOINT;

@Configuration
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
                        configurer.anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage(LOGIN_ENDPOINT)
                                .loginProcessingUrl("/authenticate-user")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .build();
    }
}