package com.example.payroll;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String firstName, String lastName){
        this(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}