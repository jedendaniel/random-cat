package com.ddd.cat.auth;

import com.ddd.cat.view.model.UserDTO;
import com.ddd.cat.view.validation.UserRegistrationValidator;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.ddd.cat.auth.AuthRole.USER;

@Service
public class UserRegistrationService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRegistrationValidator registrationValidator;

    public UserRegistrationService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder,
                                   UserRegistrationValidator registrationValidator) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.registrationValidator = registrationValidator;
    }

    public Map<String, String> register(UserDTO userDTO) {
        Map<String, String> validationErrors = registrationValidator.getValidationErrors(userDTO);
        if (userExists(userDTO)) {
            validationErrors.put("usernameInUse", "Username already in use");
        }
        if (validationErrors.isEmpty()) {
            UserDetails userDetails = User.builder()
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .roles(USER.getRole())
                    .build();
            userDetailsManager.createUser(userDetails);
        }
        return validationErrors;
    }

    private boolean userExists(UserDTO userDTO) {
        return userDetailsManager.userExists(userDTO.getUsername());
    }
}
