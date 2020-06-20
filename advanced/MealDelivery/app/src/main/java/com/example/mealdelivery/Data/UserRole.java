package com.example.mealdelivery.Data;

import com.example.mealdelivery.Roles;

import java.io.Serializable;

public class UserRole implements Serializable {
    private String uid;
    private Roles role;

    public UserRole() {}

    public UserRole(String uid, Roles role) {
        this.uid = uid;
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}

