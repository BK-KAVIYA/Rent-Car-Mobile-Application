package com.example.sourthenlankacarrental.Messages;

import com.example.sourthenlankacarrental.user.UserHelperClass;

public class BaseMessage {
    private String message;
    private String senderEmail;
    private String date;
    private String time;

    public BaseMessage() {
    }

    public BaseMessage(String message, String senderEmail, String date, String time) {
        this.message = message;
        this.senderEmail = senderEmail;
        this.date = date;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
