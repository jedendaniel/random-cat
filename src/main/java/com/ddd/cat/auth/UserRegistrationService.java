package com.ddd.cat.auth;

import com.ddd.cat.view.model.ViewUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import static com.ddd.cat.auth.AuthRole.USER;

@Service
public class UserRegistrationService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(ViewUser viewUser) {
        UserDetails userDetails = User.builder()
            .username(viewUser.getUsername())
            .password(passwordEncoder.encode(viewUser.getPassword()))
            .roles(USER.getRole())
            .build();
        userDetailsManager.createUser(userDetails);
    }
}
