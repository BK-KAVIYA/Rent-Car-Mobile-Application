package com.example.sourthenlankacarrental;

import com.google.firebase.Timestamp;

public class Cars {
    String CarName;
    String CarType;
    String FuleType;
    String NumberOfSeat;
    String Color;
    Double Price;
    Timestamp timestamp;

    public Cars() {
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public String getFuleType() {
        return FuleType;
    }

    public void setFuleType(String fuleType) {
        FuleType = fuleType;
    }

    public String getNumberOfSeat() {
        return NumberOfSeat;
    }

    public void setNumberOfSeat(String numberOfSeat) {
        NumberOfSeat = numberOfSeat;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
