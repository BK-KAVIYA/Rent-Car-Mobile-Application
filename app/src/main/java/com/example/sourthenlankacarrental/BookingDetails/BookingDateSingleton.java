package com.example.sourthenlankacarrental.BookingDetails;

public class BookingDateSingleton {
    // Private constructor to prevent instantiation from other classes
    private BookingDateSingleton() {}

    // Static inner class to hold the singleton instance
    private static class SingletonHelper {
        private static final BookingDateSingleton INSTANCE = new BookingDateSingleton();
    }

    // Method to access the singleton instance
    public static BookingDateSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // Properties for fromDate and toDate
    private String fromDate;
    private String toDate;

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
}
