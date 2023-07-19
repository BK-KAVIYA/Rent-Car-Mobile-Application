package com.example.sourthenlankacarrental.BookingDetails;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.Booking_details;
import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.DynamicItemList;
import com.example.sourthenlankacarrental.DynamicRVAdapter;
import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.Vehicle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class CheckAvailability extends AppCompatActivity {

    TextInputLayout start_date,end_date;

    Button checkAvailability;

    RecyclerView recyclerView;

    Connection connection;

    TextInputEditText start_date_txt,end_date_txt;

    String sdate,edate=null;

    ArrayList<DynamicItemList> itemVehicle=new ArrayList();

    DynamicRVAdapter1 dynamicRVAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_availability);
        getSupportActionBar().hide();

        start_date=findViewById(R.id.chstartdate);
        end_date=findViewById(R.id.chend_date);
        checkAvailability=findViewById(R.id.checkAvailability);

        start_date_txt=findViewById(R.id.check_startdate_txt);
        end_date_txt=findViewById(R.id.check_end_date_text);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        end_date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Set on click listener click");
                DatePickerDialog datePickerDialog = new DatePickerDialog(CheckAvailability.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        if (year == 0 && month == 0 && day == 0) {
                            Toast.makeText(getApplicationContext(), "Please select a valid date", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Calendar selectedStartDate = Calendar.getInstance();
                        selectedStartDate.set(year, month, day);

                        // Get the current date
                        Calendar currentDate = Calendar.getInstance();

                        // Compare the selected start date with the current date
                        if (selectedStartDate.before(currentDate)) {
                            Toast.makeText(getApplicationContext(), "Please select a start date after today", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get the selected end date
                        Calendar selectedEndDate = Calendar.getInstance();
                        selectedEndDate.set(year, month, day);

                        // Compare the selected end date with the start date
                        if (selectedEndDate.before(selectedStartDate)) {
                            Toast.makeText(getApplicationContext(), "Please select an end date after the start date", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        month = month + 1;
                        edate =  year + "-" + day + "-" + month;;
                        end_date_txt.setText(edate);
                        if (end_date_txt.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please select an end date", Toast.LENGTH_SHORT).show();
                            return;
                        }
                      //  booking.setEndDate(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });



        start_date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(CheckAvailability.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        if (year == 0 && month == 0 && day == 0) {
                            Toast.makeText(getApplicationContext(), "Please select a valid date", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, day);

                        // Get the current date
                        Calendar currentDate = Calendar.getInstance();

                        // Compare the selected date with the current date
                        if (selectedDate.before(currentDate)) {
                            Toast.makeText(getApplicationContext(), "Please select a date after today", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        month = month + 1;
                        sdate = year + "-" + day + "-" + month;

                        start_date_txt.setText(sdate);
                        if (start_date_txt.getText().toString()==null) {
                            Toast.makeText(getApplicationContext(), "Please select a start date", Toast.LENGTH_SHORT).show();
                            return;
                        }
                       // booking.setStartDate(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        RecyclerView drv = findViewById(R.id.av_rv_1);
        drv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dynamicRVAdapter = new DynamicRVAdapter1(itemVehicle);
        drv.setAdapter(dynamicRVAdapter);

        checkAvailability.setOnClickListener(new View.OnClickListener() {
            // Get the singleton instance


            @Override
            public void onClick(View v) {
                BookingDateSingleton bookingDate = BookingDateSingleton.getInstance();
                bookingDate.setFromDate(sdate);
                bookingDate.setToDate(edate);
                DBConnection dbConnection=new DBConnection();
                connection=dbConnection.getConnection();

                if (connection != null) {

                    try {
                        if(!sdate.isEmpty()) {
                            String query = "SELECT * FROM [slcrms].[dbo].[vehicle] WHERE id NOT IN (SELECT vehicle_id FROM [slcrms].[dbo].[booking] WHERE ? BETWEEN from_date AND to_date OR ? BETWEEN from_date AND to_date)";
                            PreparedStatement statement = null;
                            statement = connection.prepareStatement(query);
                            statement.setString(1, sdate);
                            statement.setString(2, edate);
                            ResultSet resultSet = statement.executeQuery();
                            itemVehicle.clear();
                            while (resultSet.next()) {
                                Vehicle vehicle = new Vehicle();
                                vehicle.setId(resultSet.getInt(1));
                                vehicle.setTitle(resultSet.getString(2));
                                vehicle.setRating(resultSet.getInt(4));
                                vehicle.setDescription(resultSet.getString(6));
                                vehicle.setImage(resultSet.getString(5));

                                itemVehicle.add(new DynamicItemList(vehicle.getId(), vehicle.getTitle(), vehicle.getDescription(), vehicle.getRating(), vehicle.getImage()));

                            }
                            dynamicRVAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getApplicationContext(), "Please select a valid date", Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        System.out.println("Exception : "+e.getMessage());
                    }



                }
                dynamicRVAdapter.notifyDataSetChanged();
            }

        });
    }
}
