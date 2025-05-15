package com.ddd.cat.auth;

public enum AuthRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    AuthRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
