package com.example.mealdeliveryadmin;

public enum Roles {
    ADMIN("admin"),
    CUSTOMER("customer");

    private String value;

    Roles(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
