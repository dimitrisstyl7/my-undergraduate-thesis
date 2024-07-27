package com.dimstyl.dietitianhub.security;

import com.dimstyl.dietitianhub.entities.Role;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Stream;

import static com.dimstyl.dietitianhub.enums.UserRole.CLIENT;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        // Check if user exists
        if (user == null) {
            throw new UsernameNotFoundException("User with username %s not found.".formatted(username));
        }

        // TODO: May I handle differently the following checks?

        // Check if user has unauthorized role
        String role = user.getRole().getName();
        if (role.equals(CLIENT.getRole())) {
            throw new AccessDeniedException("User with username %s has unauthorized role (%s).".formatted(username, role));
        }

        // Check if user is enabled
        if (!user.isEnabled()) {
            throw new DisabledException("User with username %s is disabled.".formatted(username));
        }

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                mapRoleToAuthority(user.getRole()),
                "%s %s".formatted(user.getUserInfo().getFirstName(), user.getUserInfo().getLastName()),
                user.getUserInfo().getGender()
        );
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Role role) {
        return Stream.of(role).map(r -> new SimpleGrantedAuthority(r.getName())).toList();
    }

}
