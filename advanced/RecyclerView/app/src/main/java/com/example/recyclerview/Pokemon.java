package com.example.recyclerview;

public class Pokemon {

    private String name;
    private String img_path;

    public Pokemon(String name, String img_path) {
        this.name = name;
        this.img_path = img_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
}
