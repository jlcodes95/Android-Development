package com.example.labtest1;

import androidx.annotation.NonNull;

public class Coordinates {
    public final double latitude;
    public final double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + this.latitude + ", " + this.longitude + ")";
    }
}
