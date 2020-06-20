package com.example.mealdelivery.DataTransferObjects;

import com.example.mealdelivery.Roles;

import java.io.Serializable;

public class UserRoleDto implements Serializable {
    private String uid;
    private Roles role;

    public UserRoleDto() {}

    public UserRoleDto(String uid, Roles role) {
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

