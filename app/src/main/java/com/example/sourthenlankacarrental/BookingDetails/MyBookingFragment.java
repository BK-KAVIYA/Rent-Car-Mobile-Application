package com.example.sourthenlankacarrental.BookingDetails;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.Dashboard;
import com.example.sourthenlankacarrental.HomeFragment;
import com.example.sourthenlankacarrental.Login;
import com.example.sourthenlankacarrental.Payment.PaymentActivity;
import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.user.UserSingleton;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyBookingFragment extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookingManager bookingManager;
    private List<Booking> bookingList;

    private Button HomeButton;
    Connection connection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);
        getSupportActionBar().hide();

        HomeButton=findViewById(R.id.hmbtn);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookingFragment.this, Dashboard.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.booking_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            DBConnection dbConnection=new DBConnection();
            connection=dbConnection.getConnection();
            if (connection != null) {
                String currentUserEmail = UserSingleton.getInstance().getUserEmail();

                String query = "SELECT * FROM [slcrms].[dbo].[booking] WHERE customer_email = ? AND is_complete = 1";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, currentUserEmail);

                ResultSet resultSet = statement.executeQuery();
                bookingList = new ArrayList<>();
                while (resultSet.next()) {
                    int vehicle_id = resultSet.getInt("vehicle_id");
                    System.out.println("----------------"+resultSet.getInt("vehicle_id"));
                    String query1 = "SELECT * FROM [slcrms].[dbo].[vehicle] WHERE id = ?";
                    PreparedStatement statement1 = connection.prepareStatement(query1);
                    statement1.setInt(1, vehicle_id);

                    ResultSet resultSet1 = statement1.executeQuery();

                    if (resultSet1.next()) {
                        System.out.println("-----------------"+resultSet1.getString(2));
                        bookingList.add(new Booking(resultSet1.getString(2), resultSet.getString("from_date"), resultSet.getString("to_date"), resultSet.getInt("status"), resultSet1.getString("image")));
                    } else {
                        System.out.println("password incorrect");
                    }



                }
                bookingManager = new BookingManager(this, bookingList);
                recyclerView.setAdapter(bookingManager);
                resultSet.close();
                statement.close();
            }


            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        bookingList = new ArrayList<>();
//        bookingList.add(new Booking("Range Rover", "23-06-2023", "25-06-2023", 1));
//        bookingList.add(new Booking("Range Rover", "25-06-2023", "28-06-2023", 0));
//        bookingList.add(new Booking("Range Rover", "30-06-2023", "05-07-2023", 0));
//
//        bookingManager = new BookingManager(this, bookingList);
//        recyclerView.setAdapter(bookingManager);
    }


}
