package com.example.labtest1;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Person implements Parcelable {

    private String firstName;
    private String lastName;
    private int age;
    private double latitude;
    private double longitude;
    private String coordinates;
    private String photoURL;

    public Person(String firstName, String lastName, int age, double latitude, double longitude, String photoURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoURL = photoURL;
    }

    protected Person(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        age = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        photoURL = in.readString();
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCoordinates(){
        return "(" + this.latitude + ", " + this.longitude + ")";
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeInt(this.age);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.photoURL);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[0];
        }
    };
}
