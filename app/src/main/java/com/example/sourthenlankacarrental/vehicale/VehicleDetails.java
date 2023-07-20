package com.example.sourthenlankacarrental.vehicale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.sourthenlankacarrental.BookingDetails.BookingSingleton;
import com.example.sourthenlankacarrental.Booking_details;
import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleDetails extends AppCompatActivity {
    private TextView power, speed, average_fule, passanger, doors, aircondition, gear, gas, price;

    ImageView vehicleSpec;

    Button button;

    Connection connection;

    CollapsingToolbarLayout collapsingToolbarLayout;

    int vehicleId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        if (intent != null) {
            // Retrieve the saved values from the bundle
            vehicleId = getIntent().getIntExtra("vid", 1);

        }
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_details);
        power = findViewById(R.id.power);
        speed = findViewById(R.id.specspeed);
        average_fule = findViewById(R.id.average_fule);
        passanger = findViewById(R.id.passenger);
        doors = findViewById(R.id.doors);
        aircondition = findViewById(R.id.aircondition);
        gear = findViewById(R.id.gear);
        gas = findViewById(R.id.gas);
        price = findViewById(R.id.price);
        button = findViewById(R.id.bk_det_btn);
        vehicleSpec=findViewById(R.id.clospImageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, Booking_details.class);
                // Pass any extra data to the new activity if needed
                intent.putExtra("vid", vehicleId);
                context.startActivity(intent);
            }
        });

        DBConnection dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        if (connection != null) {

            try {
                String query = "SELECT * FROM [slcrms].[dbo].[vehicle_specification] WHERE vehicle_id = ?";
                PreparedStatement statement = null;
                statement = connection.prepareStatement(query);
                statement.setInt(1, vehicleId);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    power.setText(resultSet.getString(8));
                    speed.setText(resultSet.getString(10));
                    average_fule.setText(resultSet.getString(9));
                    String pass = resultSet.getString(3) + " Passengers";
                    passanger.setText(pass);
                    String dor = resultSet.getString(7) + " Doors";
                    doors.setText(dor);
                    String air;
                    if (resultSet.getInt(5) == 1) {
                        air = "Air Condition";
                    } else {
                        air = "Non AC";
                    }
                    aircondition.setText(air);
                    String gea;
                    String fgea = resultSet.getString(6);
                    if (fgea.equals("M")) {
                        gea = "Manual";
                    } else {
                        gea = "Auto";
                    }
                    gear.setText(gea);
                    aircondition.setText(air);
                    String fule;
                    String fval = resultSet.getString(4);
                    if (fval.equals("D")) {
                        fule = "Deasel";
                    } else {
                        fule = "Petrol";
                    }
                    gas.setText(fule);

                    if ('M' == 'K') {

                    }


                    String query1 = "SELECT * FROM [slcrms].[dbo].[vehicle] WHERE id = ?";
                    PreparedStatement statement1 = null;
                    statement1 = connection.prepareStatement(query1);
                    statement1.setInt(1, vehicleId);

                    ResultSet resultSet1 = statement1.executeQuery();

                    if (resultSet1.next()) {
                        collapsingToolbarLayout.setTitle(resultSet1.getString(2));
                        BookingSingleton bookingDetails = BookingSingleton.getInstance();
                        bookingDetails.setVehicleName(resultSet1.getString(2));
                        bookingDetails.setNumber(resultSet1.getString(3));
                        String pricePerDay = "Rs " + resultSet1.getString("price_per_day") + "/= per day";
                        price.setText(pricePerDay);
                        if (resultSet1.getString(5) != null) {
                            Glide.with(vehicleSpec.getContext())
                                    .load(resultSet1.getString(5))
                                    .into(vehicleSpec);
                        }
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
