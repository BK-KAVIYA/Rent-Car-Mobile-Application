package com.example.sourthenlankacarrental;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Booking_details extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextInputEditText start_date;
    TextInputEditText end_date;
    ImageView imageViewVehicle,nicimage;

    Button booking_btn;
    private int vehicleId;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public Booking_details(int id){
        this.vehicleId=id;
        onLoad(vehicleId);
    }
    public Booking_details(){

    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;


    }

    DatePickerDialog.OnDateSetListener dateSetListener;

    String[] gender={"Male","Female"};
    String[] district={"Colombo","Gampaha","Kalutara","Kandy","Matale","Nuwara Eliya","Galle","Matara","Hambantota","Jaffna","Kilinochchi","Mannar","Vavuniya","Mullaitivu","Batticaloa","Ampara","Trincomalee","Kurunegala","Puttalam","Anuradhapura","Polonnaruwa","Badulla","Moneragala","Ratnapura","Kegalle"};

private final int GALLERY_REQ_CODE=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        getSupportActionBar().hide();



        start_date=findViewById(R.id.startdate);
        end_date=findViewById(R.id.end_date);
        booking_btn=findViewById(R.id.booking_det);
        imageViewVehicle=findViewById(R.id.booking_image_view);







        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Booking_details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        end_date.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Booking_details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        end_date.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        booking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    System.out.println("this is calling");
                    Intent intent=new Intent(Booking_details.this,GaurantorDetails.class);
                    startActivity(intent);
                }
            });


        nicimage=findViewById(R.id.nicgallery);
        Button btnGallery=findViewById(R.id.nicbtn);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent niccpy=new Intent(Intent.ACTION_PICK);
                niccpy.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(niccpy,GALLERY_REQ_CODE);
            }
        });


        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        Spinner spin = findViewById(R.id.gender);
        spin.setOnItemSelectedListener(this);
        Spinner spin1 = findViewById(R.id.district);
        spin1.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                gender);

        ArrayAdapter ad1
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                district);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        ad1.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);
        spin1.setAdapter(ad1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode==GALLERY_REQ_CODE){
                nicimage.setImageURI(data.getData());
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // make toastof name of course
        // which is selected in spinner
        Toast.makeText(getApplicationContext(),
                        gender[position],
                        Toast.LENGTH_LONG)
                .show();

        Toast.makeText(getApplicationContext(),
                        district[position],
                        Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onLoad(int vid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        Query query = databaseReference.child("vehicle").orderByChild("id").equalTo(vid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Vehicle> vehicles = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        System.out.println("Test this one===============");
                        Vehicle vehicle = snapshot.getValue(Vehicle.class);
                        vehicles.add(vehicle);
                    }

                    for (Vehicle vehicle : vehicles) {
                        System.out.println("=====================" + vehicle.getImage() + "===="+getVehicleId());
                        if (vehicle.getImage() != null) {

                            Glide.with(imageViewVehicle.getContext())
                                    .load(vehicle.getImage())
                                    .into(imageViewVehicle);
                        }
                    }
                } else {
                    System.out.println("Data does not exist!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
                // ...
            }
        });

    }


    }