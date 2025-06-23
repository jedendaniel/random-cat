package com.ddd.cat.view.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserDTO implements UserDetails {
    private final String username;
    private final String password;
    private final String matchingPassword;
    private final String email;

    public UserDTO(String username, String password, String matchingPassword, String email) {
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "USER");
    }
}
