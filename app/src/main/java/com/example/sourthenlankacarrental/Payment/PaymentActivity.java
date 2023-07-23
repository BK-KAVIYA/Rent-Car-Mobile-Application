package com.example.sourthenlankacarrental.Payment;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.sourthenlankacarrental.BookingDetails.BookingSingleton;
import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.Invoice.Invoice;
import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.notification.NotificationFragment;
import com.example.sourthenlankacarrental.notification.NotificationGenarator;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(PaymentActivity.this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(PaymentActivity.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }
      //  showNotification("Payment Confirmation","Payment has been successfully processed");

        Intent intent=getIntent();
        if (intent != null) {
            // Retrieve the saved values from the bundle
            BookingId = getIntent().getIntExtra("BID",10);
            price = getIntent().getDoubleExtra("Price",1000);
            BookingSingleton bookingDetails = BookingSingleton.getInstance();
            bookingDetails.setAmount(String.valueOf(price));
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
                            String query1 = "UPDATE [slcrms].[dbo].[booking] SET is_complete = 1 WHERE id=?;";
                            PreparedStatement statement1 = null;
                            statement1 = connection.prepareStatement(query1);
                            statement1.setInt(1, BookingId);
                            int rowsAffected1 = statement1.executeUpdate();
                            if (rowsAffected1 > 0) {

                                  showNotification("Payment Confirmation","Your Payment is successfully!");
                                  NotificationGenarator notificationGenarator=new NotificationGenarator("Payment Confirmation","Your Payment is successfully!");
                                  notificationGenarator.addNotification();

                                  Intent intent = new Intent(PaymentActivity.this, Invoice.class);
                                  startActivity(intent);
                            }else{
                                Context context = getApplicationContext();
                                Toast.makeText(context, "booking can't complete!", Toast.LENGTH_SHORT).show();
                            }
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
    public void showNotification(String title,String context){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"My Notification");
        builder.setContentTitle(title);
        builder.setContentText(context);
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent=new Intent(getApplicationContext(),NotificationFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(), 0,intent,PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        //Context context = getContext();

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=notificationManager.getNotificationChannel("My Notification");
            if(notificationChannel==null){
                NotificationChannel channel=new NotificationChannel("My Notification","My Notification", android.app.NotificationManager.IMPORTANCE_DEFAULT);
                channel.setLightColor(Color.GREEN);
                channel.enableVibration(true);
                notificationManager.createNotificationChannel(channel);
            }

        }
        notificationManager.notify(0,builder.build());


    }
}
