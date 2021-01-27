package com.example.pickt.model;

public class TrailerModel {
    private String id, trailerName, license, rentalPlace, capacity, facilities, description;

    public TrailerModel(String jsonObjectString, String name, String string, String rentalPlace, String id, String trailerName, String license){
        this.id = id;
        this.trailerName = trailerName;
        this.license = license;
        this.rentalPlace = this.rentalPlace;
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

    public String getLicense(){
        return license;
    }

    public String getRentalPlace(){
        return rentalPlace;
    }

    public String getCapacity(){
        return capacity;
    }

    public String getFacilities(){
        return facilities;
    }

    public String getDescription(){
        return description;
    }
}
