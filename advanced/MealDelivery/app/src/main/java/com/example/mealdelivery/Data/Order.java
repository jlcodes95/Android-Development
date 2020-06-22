package com.example.mealdelivery.Data;

import android.os.LocaleList;
import android.util.Log;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

public class Order implements Serializable {

    private final int DEFAULT_ORDER_ID_LENGTH = 10;
    private final double TAX_RATE = 0.13;
    private String orderId;
    private String orderedBy;
    private int subscriptionType;
    private Subscription subscription;
    private double tax;
    private double total;
    private OrderStatus orderStatus;
    private boolean isFirstPickup;
    private long orderDate;
    private long nextPickupTimestamp;

    public Order(){}

    public Order(String orderedBy, int subscriptionType, Subscription subscription) {
        this.orderId = generateOrderId();
        this.orderedBy = orderedBy;
        this.subscriptionType = subscriptionType;
        this.subscription = subscription;
        this.tax = calculateTax();
        this.total = calculateTotal();
        this.orderStatus = OrderStatus.IN_PREPARATION;
        this.isFirstPickup = true;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public long getNextPickupTimestamp() {
        return nextPickupTimestamp;
    }

    public void setNextPickupTimestamp(long nextPickupTimestamp) {
        this.nextPickupTimestamp = nextPickupTimestamp;
    }

    private String generateOrderId() {
        Random random = new Random();

        String charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DEFAULT_ORDER_ID_LENGTH; i++) {
            sb.append(charArray.charAt(random.nextInt(charArray.length())));
        }
        return sb.toString();
    }

    private double calculateSubtotal() {
        if (this.subscriptionType == Subscription.COUNT_SUBSCRIPTION_MONTH) {
            return this.subscription.getPrice();
        }
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
        DecimalFormat df = new DecimalFormat("0.00");
        return "$" + df.format(this.tax);
    }

    public String getTotalDesc() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "$" + df.format(this.total);
    }

    public void setDefaultDates() {
        LocalDateTime dateTime = LocalDateTime.now();
        this.orderDate = dateTime.toEpochSecond(ZoneOffset.UTC);
        this.nextPickupTimestamp = dateTime.plus(1, ChronoUnit.WEEKS).toEpochSecond(ZoneOffset.UTC);
    }

    public String getPickupDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(this.nextPickupTimestamp, 0, ZoneOffset.UTC);
        return dateTime.format(formatter);
    }

    public boolean pickupAvailable() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) >= this.nextPickupTimestamp;
    }

    public void setNextPickupTime() {
        if (this.isFirstPickup) {
            LocalDateTime date =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(this.orderDate), ZoneOffset.UTC);
            this.nextPickupTimestamp = date.plus(this.subscriptionType, ChronoUnit.MONTHS).toEpochSecond(ZoneOffset.UTC);
            this.isFirstPickup = false;
        }else {
            this.nextPickupTimestamp = LocalDateTime.now().plus(this.subscriptionType, ChronoUnit.MONTHS).toEpochSecond(ZoneOffset.UTC);
        }
    }
}
