package com.example.sourthenlankacarrental.notification;

public class Notification {
    private String title;
    private String message;
    private int image;
    private double date;

    public Notification(String title, String message, int image, double date) {
        this.title = title;
        this.message = message;
        this.image = image;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getDate() {
        return date;
    }

    public void setDate(float date) {
        this.date = date;
    }
}
