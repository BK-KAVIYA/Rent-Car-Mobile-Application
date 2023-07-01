package com.example.sourthenlankacarrental;

import com.google.firebase.database.PropertyName;

public class Vehicle {

    private int id;
    private String Registration_Number;
    private int ac;
    private double addition_price_per_hour;
    private String fule_type;
    private String image;
    private String insurance_revenue_date;
    private int is_deleted;
    private int is_service_out;
    private String lision_revenue_date;
    private double price_per_day;
    private int seat;
    private String title;
    private String transmit;

    private String description;

    private int rating;


    public Vehicle() {
    }

    public Vehicle(int id,String Registration_Number, int ac, double addition_price_per_hour, String fule_type, String image, String insurance_revenue_date, int is_deleted, int is_service_out, String lision_revenue_date, double price_per_day, int seat, String title, String transmit) {
        this.id=id;
        this.Registration_Number = Registration_Number;
        this.ac = ac;
        this.addition_price_per_hour = addition_price_per_hour;
        this.fule_type = fule_type;
        this.image = image;
        this.insurance_revenue_date = insurance_revenue_date;
        this.is_deleted = is_deleted;
        this.is_service_out = is_service_out;
        this.lision_revenue_date = lision_revenue_date;
        this.price_per_day = price_per_day;
        this.seat = seat;
        this.title = title;
        this.transmit = transmit;
    }

    public double getAddition_price_per_hour() {
        return addition_price_per_hour;
    }

    public void setAddition_price_per_hour(double addition_price_per_hour) {
        this.addition_price_per_hour = addition_price_per_hour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTransmit() {
        return transmit;
    }

    public void setTransmit(String transmit) {
        this.transmit = transmit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getRegistration_Number() {
        return Registration_Number;
    }

    public void setRegistration_Number(String Registration_Number) {
        this.Registration_Number = Registration_Number;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }



    public String getFule_type() {
        return fule_type;
    }

    public void setFule_type(String fule_type) {
        this.fule_type = fule_type;
    }



    public String getInsurance_revenue_date() {
        return insurance_revenue_date;
    }

    public void setInsurance_revenue_date(String insurance_revenue_date) {
        this.insurance_revenue_date = insurance_revenue_date;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getIs_service_out() {
        return is_service_out;
    }

    public void setIs_service_out(int is_service_out) {
        this.is_service_out = is_service_out;
    }

    public String getLision_revenue_date() {
        return lision_revenue_date;
    }

    public void setLision_revenue_date(String lision_revenue_date) {
        this.lision_revenue_date = lision_revenue_date;
    }

    public double getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(double price_per_day) {
        this.price_per_day = price_per_day;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

