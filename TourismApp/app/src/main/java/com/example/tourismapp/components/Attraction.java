package com.example.tourismapp.components;

import org.jetbrains.annotations.NotNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "attractions")
public class Attraction implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String description;

    private String imagePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getShortenedDescription(){
        if (description.length() > 65){
            return  description.substring(0, 65) + "...";
        }
        return description;
    }
}
