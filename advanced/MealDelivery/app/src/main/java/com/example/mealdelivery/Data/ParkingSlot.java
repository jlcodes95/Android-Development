package com.example.mealdelivery.Data;

public class ParkingSlot {

    public static final int SLOT_1 = 1;
    public static final int SLOT_2 = 2;
    public static final int SLOT_3 = 3;
    private String name;
    private Order order;
    private int slot;

    public ParkingSlot() {}

    public ParkingSlot(String name, Order order, int slot) {
        this.name = name;
        this.order = order;
        this.slot = slot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
