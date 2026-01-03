package com.ddd.cat.user;

import com.ddd.cat.model.User;
import com.ddd.cat.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

public interface UserAccountService {
    User register(User user);

    @Service
    class DefaultUserAccountService implements UserAccountService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public DefaultUserAccountService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public User register(User user) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userRepository.save(user);
        }
    }
}
