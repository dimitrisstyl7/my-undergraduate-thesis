package gr.unipi.thesis.dimstyl.security;

import gr.unipi.thesis.dimstyl.enums.Gender;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final String fullName;
    private final Gender gender;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                             String fullName, Gender gender) {
        super(username, password, authorities);
        this.fullName = fullName;
        this.gender = gender;
    }

}
