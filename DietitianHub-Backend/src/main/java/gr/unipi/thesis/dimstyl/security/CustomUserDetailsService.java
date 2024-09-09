package gr.unipi.thesis.dimstyl.security;

import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.enums.UserRole;
import gr.unipi.thesis.dimstyl.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // Check if user exists
        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("User with username %s not found.".formatted(username)));

        // TODO: May I handle differently the following check?

        // Check if the user has the role CLIENT and is already enabled
        UserRole role = user.getRole();
        if (role.equals(UserRole.CLIENT) && user.isEnabled()) {
            throw new AccessDeniedException("The client with username %s is already enabled.".formatted(username));
        }

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                mapRoleToAuthority(user.getRole()),
                "%s %s".formatted(user.getUserInfo().getFirstName(), user.getUserInfo().getLastName()),
                user.getUserInfo().getGender()
        );
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(UserRole role) {
        return Stream.of(role).map(r -> new SimpleGrantedAuthority(role.toString())).toList();
    }

    public static CustomUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public static void logout(HttpServletRequest request) throws ServletException {
        // Invalidate the session to clear all session data
        request.getSession().invalidate();

        // Perform the actual logout process
        request.logout();

        // Clear the security context to remove authentication details
        SecurityContextHolder.clearContext();
    }

}
