package com.example.pickt.model;

public class TrailerModel {
    private String id, trailerName, rentalPlace, license, capacity, facilities, description;

    public TrailerModel(String id, String trailerName, String license, String rentalPlace, String capacity, String facilities, String description){
        this.id = id;
        this.trailerName = trailerName;
        this.license = license;
        this.rentalPlace = rentalPlace;
        this.capacity = capacity;
        this.facilities = facilities;
        this.description = description;
    }

    public String getId(){
        return id;
    }

    public String getTrailerName(){
        return trailerName;
    }

    public void setTrailerName(String trailerName){
        this.trailerName = trailerName;
    }

    public String getLicense(){
        return license;
    }

    public void setLicense(String license){
        this.license = license;
    }

    public String getRentalPlace(){
        return rentalPlace;
    }

    public void setRentalPlace(String rentalPlace){
        this.rentalPlace = rentalPlace;
    }

    public String getCapacity(){
        return capacity;
    }

    public void setCapacity(String capacity){
        this.capacity = capacity;
    }
    public String getFacilities(){
        return facilities;
    }

    public void setFacilities(String facilities){
        this.facilities = facilities;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
