package com.example.pickt;

public class Car {
    private int id;
    private String name;
    private String license;
    private byte[] image;

    public Car(String name, String license, byte[] image, int id) {
        this.name = name;
        this.license = license;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}