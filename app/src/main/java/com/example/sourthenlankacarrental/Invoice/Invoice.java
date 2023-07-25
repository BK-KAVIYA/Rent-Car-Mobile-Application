package com.example.sourthenlankacarrental.Invoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sourthenlankacarrental.BookingDetails.BookingSingleton;
import com.example.sourthenlankacarrental.BookingDetails.MyBookingFragment;
import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.Payment.PaymentActivity;
import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.notification.Notification;
import com.example.sourthenlankacarrental.notification.NotificationManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Invoice extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String FILE_NAME = "invoice.pdf";
    private Button printButton,myBooking;

    private TextView InvoiceNumber;

    Connection connection;

    TextView customerName,customerAddress,billingDate,vehicleDetails,fromDate,toDate,price,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        printButton = findViewById(R.id.buttonPrint);
        customerName=findViewById(R.id.textViewCustomerName);
        customerAddress=findViewById(R.id.textViewCustomerAddress);
        billingDate=findViewById(R.id.textViewDate);
        vehicleDetails=findViewById(R.id.textViewVehicle);
        fromDate=findViewById(R.id.textViewFrom1);
        toDate=findViewById(R.id.textViewTo1);
        price=findViewById(R.id.textViewPrice1);
        total=findViewById(R.id.textViewTotal);
        myBooking=findViewById(R.id.buttonHome);
        InvoiceNumber=findViewById(R.id.textViewInvoiceNumber);


        DBConnection dbConnection=new DBConnection();
        connection=dbConnection.getConnection();


        try {
            String query = "SELECT TOP 1 [id] FROM [slcrms].[dbo].[transaction] ORDER BY [id] DESC;";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                InvoiceNumber.setText("Invoice Number: #00"+resultSet.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        BookingSingleton bookingDetails = BookingSingleton.getInstance();
        customerName.setText("Name :"+bookingDetails.getCustomerName());
        customerAddress.setText(bookingDetails.getCustomerAddress());

        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
            String formattedDate = currentDate.format(formatter);
            billingDate.setText(formattedDate);
        }

        vehicleDetails.setText(bookingDetails.getVehicleName()+"\n"+bookingDetails.getNumber());
        fromDate.setText(bookingDetails.getFromDate());
        toDate.setText(bookingDetails.getToDate());
        price.setText(bookingDetails.getAmount());
        total.setText(bookingDetails.getAmount());

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printInvoice();
            }
        });
        myBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Invoice.this, MyBookingFragment.class);
                startActivity(intent);
            }
        });
    }

    private void printInvoice() {
        // Create a PrintManager instance
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

        // Set a print job name
        String jobName = getString(R.string.app_name) + " Document";

        // Get the content view to be printed
        View contentView = getWindow().getDecorView().findViewById(R.id.printlayout);

        // Create a print document adapter
        PrintDocumentAdapter printAdapter = new InvoicePrintAdapter(this, contentView);

        // Start the print job
        printManager.print(jobName, printAdapter, null);
    }
}