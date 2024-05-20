package com.dimstyl.dietitianhub.security;

import com.dimstyl.dietitianhub.entities.Role;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            // Todo: Handle it from a global exception handler
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        if (!user.isEnabled()) {
            // Todo: Handle it from a global exception handler
            throw new DisabledException("Your account is disabled, please contact the administrator.");
        }

        return new org.springframework.security.core.userdetails.User(
                username, user.getPassword(), mapRoleToAuthority(user.getRole())
        );
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Role role) {
        return Stream.of(role).map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
