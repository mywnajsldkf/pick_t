package com.example.pickt.model;

public class TrailerModel {
    private String id, userId, trailerName, rentalPlace, cost, license, capacity, facilities, description, trailerPhoto;

    public TrailerModel(String id, String userId, String trailerName, String license, String rentalPlace, String cost, String capacity, String facilities, String description, String trailerPhoto){
        this.id = id;
        this.userId = userId;
        this.trailerName = trailerName;
        this.license = license;
        this.rentalPlace = rentalPlace;
        this.cost = cost;
        this.capacity = capacity;
        this.facilities = facilities;
        this.description = description;
        this.trailerPhoto = trailerPhoto;
    }

    public String getId(){
        return id;
    }

    public String getUserId(){ return userId; }

    public void setUserId(String userId) { this.userId = userId; }

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

    public String getCost(){
        return cost;
    }

    public void setCost(String cost){
        this.cost = cost;
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

    public String getTrailerPhoto(){
        return trailerPhoto;
    }

    public void setTrailerPhoto(String trailerPhoto){
        this.trailerPhoto = trailerPhoto;
    }
}
