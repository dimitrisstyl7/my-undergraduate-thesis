package gr.unipi.thesis.dimstyl.security;

import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.enums.RequestType;
import gr.unipi.thesis.dimstyl.enums.UserRole;
import gr.unipi.thesis.dimstyl.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final String uri = request.getServletPath();

        Optional<User> optionalUser = userRepository.findByUsername(username);

        // Check if user exists
        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("User with username %s not found.".formatted(username)));

        // If user is a client, is enabled and is not trying to preview an announcement or an article,
        // then he is not authorized to access the web platform
        boolean isWebRequest = RequestType.byUri(uri).equals(RequestType.WEB);
        boolean hasRoleClient = user.getRole().equals(UserRole.CLIENT);
        boolean previewRequest = (uri.startsWith("/announcements/") && uri.endsWith("/preview")) ||
                                 (uri.startsWith("/articles/") && uri.endsWith("/preview"));

        if (isWebRequest && hasRoleClient && !previewRequest && user.isEnabled()) {
            throw new AccessDeniedException("User with username %s is not authorized to access the web platform.".formatted(username));
        }

        // If user is a dietitian and request type is API, then he is not authorized to access the API
        boolean isApiRequest = RequestType.byUri(uri).equals(RequestType.API);
        boolean hasRoleDietitian = user.getRole().equals(UserRole.DIETITIAN);
        if (isApiRequest && hasRoleDietitian) {
            throw new AccessDeniedException("User with username %s is not authorized to access the API.".formatted(username));
        }

        return new CustomUserDetails(user);
    }

    public CustomUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public void logout(HttpServletRequest request) throws ServletException {
        // Invalidate the session to clear all session data
        request.getSession().invalidate();

        // Perform the actual logout process
        request.logout();

        // Clear the security context to remove authentication details
        SecurityContextHolder.clearContext();
    }

}
