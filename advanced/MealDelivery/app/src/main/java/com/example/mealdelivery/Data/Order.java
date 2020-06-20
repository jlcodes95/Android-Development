package com.example.mealdelivery.Data;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Random;

public class Order implements Serializable {

    private final int DEFAULT_ORDER_ID_LENGTH = 10;
    private final DecimalFormat df = new DecimalFormat("0.00");
    private final double TAX_RATE = 0.13;
    private String orderId;
    private String orderedBy;
    private int subscriptionType;
    private Subscription subscription;
    private double tax;
    private double total;

    public Order(String orderedBy, int subscriptionType, Subscription subscription) {
        this.orderId = generateOrderId();
        this.orderedBy = orderedBy;
        this.subscriptionType = subscriptionType;
        this.subscription = subscription;
        this.tax = calculateTax();
        this.total = calculateTotal();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public int getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(int subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private String generateOrderId() {
        Random random = new Random();

        String[] charArray = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DEFAULT_ORDER_ID_LENGTH; i++) {
            sb.append(charArray[random.nextInt(charArray.length)]);
        }
        return sb.toString();
    }

    private double calculateSubtotal() {
        return (this.subscription.getPrice() * this.subscriptionType) - this.subscription.getDiscount();
    }

    private double calculateTotal() {
        return calculateSubtotal() + calculateTax();
    }

    private double calculateTax() {
        return calculateSubtotal() * TAX_RATE;
    }

    public String getSubtotalDesc() {
        return this.subscription.getTotalDesc(this.subscriptionType);
    }

    public String getTaxDesc() {
        return "$" + df.format(this.tax);
    }

    public String getTotalDesc() {
        return "$" + df.format(this.total);
    }
}
