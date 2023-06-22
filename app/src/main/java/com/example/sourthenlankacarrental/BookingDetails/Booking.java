package com.example.sourthenlankacarrental.BookingDetails;

public class Booking {
    private String title;
    private String startDate;
    private String endDate;
    private String status;
    private String name;
    private String phone;
    private String nic;
    private String gender;
    private String age;
    private String district;
    private String userEmail;

    private int driverStatus;
    private String nic_url;

    private String vehicle_id;

    public Booking() {
    }

    public Booking(String startDate, String endDate, String status, String name, String phone, String nic, String gender, String age, String district) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.name = name;
        this.phone = phone;
        this.nic = nic;
        this.gender = gender;
        this.age = age;
        this.district = district;
    }

    public Booking(String title, String startDate, String endDate, String status) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(int driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNic_url() {
        return nic_url;
    }

    public void setNic_url(String nic_url) {
        this.nic_url = nic_url;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
}
