package com.example.mealdeliveryadmin.DataTransferObjects;

public class User {
    private String firstName;
    private String lastName;
    private String uid;
    private String[] roles;

    public User(String firstName, String lastName, String uid, String[] roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uid = uid;
        this.roles = roles;
    }
}
