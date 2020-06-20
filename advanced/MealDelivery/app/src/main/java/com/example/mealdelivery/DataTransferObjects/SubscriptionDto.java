package com.example.mealdelivery.DataTransferObjects;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SubscriptionDto implements Serializable {

    public static final int COUNT_SUBSCRIPTION_MONTH = 1;
    public static final int COUNT_SUBSCRIPTION_SEMI_ANNUAL = 6;
    private final int COUNT_MONTH = 30;
    private String name;
    private double price;
    private double discount;
    private String allergy;
    private String description;
    private String photoUrl;
    private ArrayList<String> samplePhotoUrls;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public SubscriptionDto() {}

    public SubscriptionDto(String name, double price, double discount, String allergy, String description, String photoPath, ArrayList<String> samplePhotoUrls) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.allergy = allergy;
        this.description = description;
        this.photoUrl = photoPath;
        this.samplePhotoUrls = samplePhotoUrls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public ArrayList<String> getSamplePhotoUrls() {
        return samplePhotoUrls;
    }

    public void setSamplePhotoUrls(ArrayList<String> samplePhotoUrls) {
        this.samplePhotoUrls = samplePhotoUrls;
    }

    private double getPerMealPrice(int months) {
        double price = (this.price - this.discount) / COUNT_MONTH;
        return this.price / months;
    }

    public String getPerMealPriceDesc() {
        return "Starting at $" + df.format(getPerMealPrice(COUNT_SUBSCRIPTION_MONTH)) + " per meal";
    }
}
