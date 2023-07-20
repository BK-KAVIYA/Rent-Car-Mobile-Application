package com.example.sourthenlankacarrental.BookingDetails;

public class BookingSingleton {
    // Private constructor to prevent instantiation from other classes
    private BookingSingleton() {}

    // Static inner class to hold the singleton instance
    private static class SingletonHelper {
        private static final BookingSingleton INSTANCE = new BookingSingleton();
    }

    // Method to access the singleton instance
    public static BookingSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // Properties for fromDate and toDate
    private String fromDate;
    private String toDate;

    private String amount;

    private String vehicleName;

    private String number;

    private String customerName;

    private String customerAddress;

    // Getter and Setter methods for fromDate and toDate
    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
}
