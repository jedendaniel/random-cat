package com.ddd.cat.controller;

import com.ddd.cat.controller.model.AuthRequest;
import com.ddd.cat.controller.model.AuthResponse;
import com.ddd.cat.model.Role;
import com.ddd.cat.model.User;
import com.ddd.cat.security.JwtService;
import com.ddd.cat.user.UserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserAccountService userAccountService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public UserController(UserAccountService userAccountService, AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService) {
        this.userAccountService = userAccountService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.username(), authRequest.password()));

        UserDetails user = userDetailsService.loadUserByUsername(authRequest.username());

        return ResponseEntity.ok(new AuthResponse(jwtService.generateToken(user)));
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody User user) {
        user.setRoles(Set.of(Role.USER));
        userAccountService.register(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/admin")
    public ResponseEntity<Void> registerAdmin(@RequestBody User user) {
        user.setRoles(Set.of(Role.USER, Role.ADMIN));
        userAccountService.register(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
