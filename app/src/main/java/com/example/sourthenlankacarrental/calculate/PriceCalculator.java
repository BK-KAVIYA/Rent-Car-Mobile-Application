package com.example.sourthenlankacarrental.calculate;

import android.content.Intent;

import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.Dashboard;
import com.example.sourthenlankacarrental.Login;
import com.example.sourthenlankacarrental.user.UserSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceCalculator {
    private long numberOfDays;
    private int vehicleId;
    private double pricePerDay;
    private double total;

    Connection connection;

    public PriceCalculator(long numberOfDays, int vehicleId) {
        this.numberOfDays = numberOfDays;
        this.vehicleId = vehicleId;
    }

    public double getTotalPrice(){
        try {
            DBConnection dbConnection=new DBConnection();
            connection=dbConnection.getConnection();
            if (connection != null) {

                String query = "SELECT * FROM [slcrms].[dbo].[vehicle] WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1,vehicleId);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {

                   pricePerDay=resultSet.getDouble("price_per_day");
                   total=pricePerDay*numberOfDays;
                    System.out.println("price inside class ------------"+total);

                    // Do something with the retrieved data
                }else{
                    System.out.println("Price can't calculate");
                }

                resultSet.close();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
}
