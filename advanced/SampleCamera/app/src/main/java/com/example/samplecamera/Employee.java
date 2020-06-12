package com.example.samplecamera;

import java.io.Serializable;
import java.time.LocalDate;

public class Employee implements Serializable {

    private int userId;
    private String jobTitleName;
    private String firstName;
    private String lastName;
    private String employeeCode;
    private String region;
    private String dept;
    private String phoneNumber;
    private String emailAddress;
    private LocalDate dateHired;

    public Employee(String firstName, String lastName, LocalDate dateHired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateHired = dateHired;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
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

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getDateHired() {
        return dateHired;
    }

    public void setDateHired(LocalDate dateHired) {
        this.dateHired = dateHired;
    }
}
