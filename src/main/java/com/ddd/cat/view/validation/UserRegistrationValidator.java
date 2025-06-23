package com.ddd.cat.view.validation;

import com.ddd.cat.view.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserRegistrationValidator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public Map<String, String> getValidationErrors(UserDTO userDTO) {
        return Stream.of(
                validateEmptyOrNull("Username", userDTO.getUsername()),
                validateEmptyOrNull("Password", userDTO.getPassword()),
                validateEmptyOrNull("Email", userDTO.getEmail()),
                validateMaxLength("Username", userDTO.getUsername(), 16),
                validateMaxLength("Password", userDTO.getPassword(), 16),
                validateMaxLength("Email", userDTO.getEmail(), 32),
                passwordsMatch(userDTO.getPassword(), userDTO.getMatchingPassword()),
                validateEmail(userDTO.getEmail())
        )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(message -> message.split(":"))
                .collect(Collectors.toMap(array -> array[0], array -> array[1]));
    }

    private Optional<String> validateEmptyOrNull(String propertyName, String propertyValue) {
        if (propertyValue != null && !propertyValue.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(propertyName.toLowerCase(Locale.ROOT) + "Empty:" + propertyName + " cannot be empty");
        }
    }

    private Optional<String> validateMaxLength(String propertyName, String propertyValue, int maxLength) {
        if (propertyValue == null || propertyValue.isEmpty() || propertyValue.length() <= maxLength) {
            return Optional.empty();
        } else {
            return Optional.of(String.format("%sTooLong:%s too long (max: %d characters)",
                    propertyName.toLowerCase(Locale.ROOT), propertyName, maxLength));
        }
    }

    private Optional<String> passwordsMatch(String password, String passwordConfirmation) {
        if (password.equals(passwordConfirmation)) {
            return Optional.empty();
        } else {
            return Optional.of("passwordsDoNotMatch:Passwords do not match");
        }
    }

    private Optional<String> validateEmail(String email) {
        if (email == null || email.isEmpty() || pattern.matcher(email).matches()) {
            return Optional.empty();
        } else {
            return Optional.of("invalidEmail:Invalid email");
        }
    }
}
