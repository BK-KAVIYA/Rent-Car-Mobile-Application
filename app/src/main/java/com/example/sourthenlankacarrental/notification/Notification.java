package com.example.sourthenlankacarrental.notification;

public class Notification {
    private String title;
    private String message;
    private String image;
    private String time;
    private String date;

    public Notification(String title, String message, String image, String time, String date) {
        this.title = title;
        this.message = message;
        this.image = image;
        this.time = time;
        this.date=date;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
