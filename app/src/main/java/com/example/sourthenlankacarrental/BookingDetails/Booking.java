package com.example.sourthenlankacarrental.BookingDetails;

public class Booking {
    private String title;
    private String startDate;
    private String endDate;
    private int status;
    private String name;
    private String phone;
    private String nic;
    private String gender;
    private String age;
    private String district;
    private String userEmail;
    private int driverStatus;
    private String nic_url;
    private int vehicle_id;

    private String booking_date;

    private int is_complete;

    private int is_delete;

    private String vehicle_imgUrl;
    public Booking() {
    }

    public Booking(String startDate, String endDate, int status, String name, String phone, String nic, String gender, String age, String district) {
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

    public Booking(String title, String startDate, String endDate, int status,String vehicle_imgUrl) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.vehicle_imgUrl=vehicle_imgUrl;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public int getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(int is_complete) {
        this.is_complete = is_complete;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public String getVehicle_imgUrl() {
        return vehicle_imgUrl;
    }

    public void setVehicle_imgUrl(String vehicle_imgUrl) {
        this.vehicle_imgUrl = vehicle_imgUrl;
    }
}
