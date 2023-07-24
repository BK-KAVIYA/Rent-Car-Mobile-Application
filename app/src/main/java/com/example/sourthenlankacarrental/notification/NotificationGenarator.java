package com.example.sourthenlankacarrental.notification;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.user.UserSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class NotificationGenarator {
    private String title;
    private String message;
    private String image;
    private String time;
    private String date;
    private String senderEmail;
    private Connection connection;

    public NotificationGenarator(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public void addNotification(){

        DBConnection dbConnection=new DBConnection();
        connection=dbConnection.getConnection();

        senderEmail= UserSingleton.getInstance().getUserEmail();

        // Get current date and time
        LocalDateTime now = null;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;

        int year = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
            year = now.getYear();
            month = now.getMonthValue();
            day = now.getDayOfMonth();
            hour = now.getHour();
            minute = now.getMinute();
            int second = now.getSecond();
        }

        date = year + "-" + month + "-" + day;
        time = hour + ":" + minute;

        if (connection != null) {

            String query = "INSERT INTO [slcrms].[dbo].[customer_notification] (user_email, title,message, image,time,date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(query);
                statement.setString(1, senderEmail);
                statement.setString(2, title);
                statement.setString(3, message);
                statement.setString(4, "Notification_1.jpg");
                statement.setString(5, time);
                statement.setString(6, date);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Notification is added!");
                } else {
                    System.out.println("Notification added failed!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }


}
