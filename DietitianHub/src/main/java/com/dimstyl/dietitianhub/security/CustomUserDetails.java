package com.dimstyl.dietitianhub.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final String fullName;
    private final char gender;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                             String fullName, char gender) {
        super(username, password, authorities);
        this.fullName = fullName;
        this.gender = gender;
    }

}
