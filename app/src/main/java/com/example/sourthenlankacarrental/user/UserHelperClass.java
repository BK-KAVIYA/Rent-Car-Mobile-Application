package com.example.sourthenlankacarrental.user;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserHelperClass {
    //variable
    String regName,userName,email,phonenNumber,idNumber,Password,cPassword;

    public UserHelperClass() {
    }

    public UserHelperClass(String regName, String userName, String email, String phonenNumber, String idNumber, String password, String cPassword) {
        this.regName = regName;
        this.userName = userName;
        this.email = email;
        this.phonenNumber = phonenNumber;
        this.idNumber = idNumber;
        Password = password;
        this.cPassword = cPassword;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenNumber() {
        return phonenNumber;
    }

    public void setPhonenNumber(String phonenNumber) {
        this.phonenNumber = phonenNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getcPassword() {
        return cPassword;
    }

    public void setcPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    @Override
    public String toString() {
        return "UserHelperClass{" +
                "regName='" + regName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phonenNumber='" + phonenNumber + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", Password='" + Password + '\'' +
                ", cPassword='" + cPassword + '\'' +
                '}';
    }
}


