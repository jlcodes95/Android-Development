package com.example.mealdelivery.Data;

public enum OrderStatus {
    IN_PREPARATION("IN PREPARATION"),
    READY_FOR_PICKUP("READY FOR PICKUP"),
    WAITING_AT_PARKING("WAITING AT PARKING");

    private String value;

    private OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
