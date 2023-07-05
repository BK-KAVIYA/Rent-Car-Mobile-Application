package com.example.sourthenlankacarrental.Payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourthenlankacarrental.BookingDetails.MyBookingFragment;
import com.example.sourthenlankacarrental.Booking_details;
import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.Dashboard;
import com.example.sourthenlankacarrental.GaurantorDetails;
import com.example.sourthenlankacarrental.Login;
import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.user.UserSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentActivity extends AppCompatActivity {
    Button pay_button;
    EditText amount,card_number,expire_date,cvv;

    Connection connection;
    int BookingId,vehicleID;
    double price;

    String Booking_date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pyament_getway_layout);
        getSupportActionBar().hide();

        Intent intent=getIntent();
        if (intent != null) {
            // Retrieve the saved values from the bundle
            BookingId = getIntent().getIntExtra("BID",10);
            price = getIntent().getDoubleExtra("Price",1000);
        }

        pay_button=findViewById(R.id.pay_button);
        amount =findViewById(R.id.payment_amount_edittext);
        card_number =findViewById(R.id.card_number_edittext);
        expire_date =findViewById(R.id.expiry_date_edittext);
        cvv =findViewById(R.id.cvv_edittext);
        String sprice="Rs:" +String.valueOf(price)+"0/=";
        amount.setText(sprice);

        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
            String formattedDate = currentDate.format(formatter);
            System.out.println("Date----------"+formattedDate);
            Booking_date=formattedDate;
        }
        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBConnection dbConnection=new DBConnection();
                connection=dbConnection.getConnection();
                if (connection != null) {

                    String query = "INSERT INTO [slcrms].[dbo].[transaction] (booking_id, date, amount) VALUES (?, ?, ?)";
                    PreparedStatement statement = null;
                    try {
                        statement = connection.prepareStatement(query);
                        statement.setInt(1, BookingId);
                        statement.setString(2, Booking_date);
                        statement.setDouble(3, price);

                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {
                            Intent intent = new Intent(PaymentActivity.this, MyBookingFragment.class);
                            startActivity(intent);
                        } else {
                            Context context = getApplicationContext();
                            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

    }
}
