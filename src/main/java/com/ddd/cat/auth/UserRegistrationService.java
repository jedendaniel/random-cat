package com.ddd.cat.auth;

import com.ddd.cat.view.model.ViewModelAttribute;
import com.ddd.cat.view.model.ViewUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import static com.ddd.cat.auth.AuthRole.USER;
import static com.ddd.cat.view.model.ViewModelAttribute.REGISTRATION_NAME_TOO_LONG;
import static com.ddd.cat.view.model.ViewModelAttribute.REGISTRATION_SUCCESS;
import static com.ddd.cat.view.model.ViewModelAttribute.REGISTRATION_USER_EXISTS;

@Service
public class UserRegistrationService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public ViewModelAttribute register(ViewUser viewUser) {
        if (userDetailsManager.userExists(viewUser.getUsername())) {
            return REGISTRATION_USER_EXISTS;
        } else if (viewUser.getUsername().length() > 16) {
            return REGISTRATION_NAME_TOO_LONG;
        } else {
            UserDetails userDetails = User.builder()
                    .username(viewUser.getUsername())
                    .password(passwordEncoder.encode(viewUser.getPassword()))
                    .roles(USER.getRole())
                    .build();
            userDetailsManager.createUser(userDetails);
            return REGISTRATION_SUCCESS;
        }
    }
}
