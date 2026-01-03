package com.ddd.cat.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
