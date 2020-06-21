package com.example.mealdelivery.Data;

public class ParkingSlot {

    private Order order;
    private int slot;

    public ParkingSlot() {}

    public ParkingSlot(Order order, int slot) {
        this.order = order;
        this.slot = slot;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
