package com.ddd.cat.view.model;

public enum ViewModelAttribute {
    CAT_PICTURE("catPicture"),
    LOGIN_ERROR("loginError"),
    REGISTRATION_SUCCESS("registrationSuccess"),
    REGISTRATION_NAME_TOO_LONG("registrationNameTooLong"),
    REGISTRATION_USER_EXISTS("registrationExists");

    private final String attribute;

    ViewModelAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String attribute() {
        return attribute;
    }
}