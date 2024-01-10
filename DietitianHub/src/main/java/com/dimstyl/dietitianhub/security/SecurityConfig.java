package com.dimstyl.dietitianhub.security;

import com.dimstyl.dietitianhub.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.dimstyl.dietitianhub.constants.Endpoints.*;

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
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage(LOGIN_ENDPOINT)
                                .permitAll()
                                .loginProcessingUrl("/authenticate-user")
                                .defaultSuccessUrl(INDEX_ENDPOINT, true)
                )
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ENDPOINT)))
                .build();
    }
}