/* -----------------------------------
 * John Lin
 * 101296282
 * ----------------------------------- */
package com.example.rydeapp;

public class User {

    private String username;
    private String password;
    private String phoneNumber;

    /**
     * user constructor, all fields are required
     * @param username the user name
     * @param password the password
     * @param phoneNumber the phone number
     */
    public User(String username, String password, String phoneNumber){
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    /**
     * checks if given username, password & phone number matches with the current user
     * @param username the user name
     * @param password the password
     * @param phoneNumber the phone number
     * @return true if all matches
     */
    public boolean isMatchingCredentials(String username, String password, String phoneNumber){
        return this.username.equals(username) && this.password.equals(password) && this.phoneNumber.equals(phoneNumber);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
