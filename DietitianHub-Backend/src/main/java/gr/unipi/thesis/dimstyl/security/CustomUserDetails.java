package gr.unipi.thesis.dimstyl.security;

import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.enums.Gender;
import gr.unipi.thesis.dimstyl.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails, Principal {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapRoleToAuthority(user.getRole());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    public String getFullName() {
        return user.getUserInfo().getFirstName() + " " + user.getUserInfo().getLastName();
    }

    public Gender getGender() {
        return user.getUserInfo().getGender();
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(UserRole role) {
        return Stream.of(role).map(r -> new SimpleGrantedAuthority(role.toString())).toList();
    }
}
