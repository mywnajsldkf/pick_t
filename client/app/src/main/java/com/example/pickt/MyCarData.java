package com.example.pickt;

public class MyCarData {
    private Integer carImage;
    private String carNameText;
    private String carRentText;
    private String carCostText;

    public MyCarData(Integer carImage, String carNameText, String carRentText, String carCostText){
        this.carImage = carImage;
        this.carNameText = carNameText;
        this.carRentText = carRentText;
        this.carCostText = carCostText;
    }

    public Integer getCarImage(){
        return carImage;
    }

    public void setCarImage(Integer carImage){
        this.carImage = carImage;
    }

    public String getCarNameText() {
        return carNameText;
    }

    public void setCarNameText(String carNameText) {
        this.carNameText = carNameText;
    }

    public String getCarRentText() {
        return carRentText;
    }

    public void setCarRentText(String carRentText) {
        this.carRentText = carRentText;
    }

    public String getCarCostText() {
        return carCostText;
    }

    public void setCarCostText(String carCostText) {
        this.carCostText = carCostText;
    }
}

