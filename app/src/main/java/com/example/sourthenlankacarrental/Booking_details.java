package com.example.sourthenlankacarrental;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sourthenlankacarrental.BookingDetails.Booking;
import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.user.UserHelperClass;
import com.example.sourthenlankacarrental.user.UserSingleton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Booking_details extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Connection connection;
    TextInputLayout start_date,end_date;
    TextInputEditText start_date_txt,end_date_txt,name_txt,mobile_txt,nic_txt,age_txt;
    ImageView imageViewVehicle,nicimage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Spinner district_txt;
    Button booking_btn;
    CheckBox driverStatusCheckbox;
    Booking booking=new Booking();


    private String name,mobile,nic_num,gender_txt,age,district_text,nic_cpy,start_dt,end_dt,email;
    private int vehicleId;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public Booking_details(int id){
        this.vehicleId=id;

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

    String[] gender={"Select Gender","Male","Female"};
    String[] district={"Select District","Colombo","Gampaha","Kalutara","Kandy","Matale","Nuwara Eliya","Galle","Matara","Hambantota","Jaffna","Kilinochchi","Mannar","Vavuniya","Mullaitivu","Batticaloa","Ampara","Trincomalee","Kurunegala","Puttalam","Anuradhapura","Polonnaruwa","Badulla","Moneragala","Ratnapura","Kegalle"};

private final int GALLERY_REQ_CODE=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        System.out.println("---------------------------"+getVehicleId());
        getSupportActionBar().hide();
        Intent intent=getIntent();
        if (intent != null) {
            // Retrieve the saved values from the bundle
            vehicleId = getIntent().getIntExtra("vid",1);

            //vehicleId = savedInstanceState.getInt("vid");


            // Restore the values to your variables
            // Do something with the restored values
        }




        start_date=findViewById(R.id.startdate);
        end_date=findViewById(R.id.end_date);
        start_date_txt=findViewById(R.id.startdate_txt);
        end_date_txt=findViewById(R.id.end_date_text);
        booking_btn=findViewById(R.id.booking_det);
        imageViewVehicle=findViewById(R.id.booking_image_view);
        collapsingToolbarLayout=findViewById(R.id.vehicle_title);
        district_txt=findViewById(R.id.district);
        name_txt=findViewById(R.id.name_txt);
        mobile_txt=findViewById(R.id.mobile_txt);
        nic_txt=findViewById(R.id.nic_txt);
        age_txt=findViewById(R.id.age_txt);
        driverStatusCheckbox = findViewById(R.id.driver_status);
        int driverStatus = driverStatusCheckbox.isChecked() ? 1 : 0;

        onLoad(vehicleId);







        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        end_date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Set on click listener click");
                DatePickerDialog datePickerDialog = new DatePickerDialog(Booking_details.this, new DatePickerDialog.OnDateSetListener() {
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
                        String date = day + "/" + month + "/" + year;
                        end_date_txt.setText(date);
                        if (end_date_txt.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please select an end date", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        booking.setEndDate(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });



        start_date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(Booking_details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String date=null;
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
                        date = day + "/" + month + "/" + year;

                        start_date_txt.setText(date);
                        if (start_date_txt.getText().toString()==null) {
                            Toast.makeText(getApplicationContext(), "Please select a start date", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        booking.setStartDate(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });




        booking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_txt.getText().toString().trim();
                String phone = mobile_txt.getText().toString().trim();
                String nic = nic_txt.getText().toString().trim();
                String age = age_txt.getText().toString().trim();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                String currentUserEmail = currentUser.getEmail();

                // Validate name
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!name.matches("[a-zA-Z ]+")) {
                    Toast.makeText(getApplicationContext(), "Name should contain only letters and spaces", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Set the name in the booking object
                    booking.setName(name);
                }

                // Validate phone number
                if (phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter a phone number", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!phone.matches("\\d{10}")) {
                    Toast.makeText(getApplicationContext(), "Phone number should have exactly 10 digits", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Set the phone number in the booking object
                    booking.setPhone(phone);
                }

                // Validate NIC
                if (nic.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter an NIC", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Set the NIC in the booking object
                    booking.setNic(nic);
                }

                // Validate age
                if (age.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter an age", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Set the age in the booking object
                    booking.setAge(age);
                }

                booking.setStatus("review");
                booking.setDriverStatus(driverStatus);
                booking.setUserEmail(currentUserEmail);



                    Map<String, Object> bookingData = new HashMap<>();
                    bookingData.put("name", booking.getName());
                    bookingData.put("phone", booking.getPhone());
                    bookingData.put("nic", booking.getNic());
                    bookingData.put("gender", booking.getGender());
                    bookingData.put("age", booking.getAge());
                    bookingData.put("district", booking.getDistrict());
                    bookingData.put("start_date", booking.getStartDate());
                    bookingData.put("end_date", booking.getEndDate());
                    bookingData.put("vehicle_id",getVehicleId());
                    bookingData.put("driver_status",booking.getDriverStatus());
                    bookingData.put("status",booking.getStatus());
                    bookingData.put("user_mail",booking.getUserEmail());

//                    String bookingKey = myRef.push().getKey();
//                    myRef.child(bookingKey).setValue(bookingData);
//                   // myRef.push().setValue(bookingData);
//
//                    myRef.setValue(bookingData).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                // Data saved successfully
//                                Intent intent=new Intent(Booking_details.this,GaurantorDetails.class);
//                                startActivity(intent);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Error occurred while saving data
//                                System.out.println("data insertion error");
//                            }
//                        });


                //Upload Image to Firebase datastore
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                String filename = "image_" + System.currentTimeMillis() + ".jpg";
                StorageReference imageRef = storageRef.child(filename);

                // Get the drawable from the ImageView
                Drawable drawable = nicimage.getDrawable();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();

                // Compress the image on a background thread
                new AsyncTask<Bitmap, Void, byte[]>() {
                    @Override
                    protected byte[] doInBackground(Bitmap... bitmaps) {
                        Bitmap compressedBitmap = Bitmap.createScaledBitmap(bitmaps[0], 800, 800, true);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                        return baos.toByteArray();
                    }

                    @Override
                    protected void onPostExecute(byte[] imageData) {
                        // Upload the image to Firebase Storage
                        UploadTask uploadTask = imageRef.putBytes(imageData);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Image upload success, retrieve the download URL
                                Task<Uri> downloadUrlTask = imageRef.getDownloadUrl();
                                downloadUrlTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadUrl) {
                                        // Image download URL retrieved, store it in the Firebase Realtime Database
                                        String imageUrl = downloadUrl.toString();
                                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                                        Map<String, Object> usernicData = new HashMap<>();
                                        usernicData.put("image_url", imageUrl);
                                        usernicData.put("user_email", booking.getUserEmail());

                                        databaseRef.child("customer_nic").setValue(usernicData);
                                    }
                                });
                            }
                        });
                    }
                }.execute(bitmap);

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the values to the bundle
        outState.putInt("id", getVehicleId());
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
        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        // make toastof name of course
        // which is selected in spinner
        if (parent.getId() == R.id.district) {
            // Handle Spinner 1 selection
            String selectedDistrict = district[position];
            booking.setDistrict(selectedDistrict);
        } else if (parent.getId() == R.id.gender) {
            // Handle Spinner 2 selection
            String selectedGender = gender[position];
            booking.setGender(selectedGender);
        }
//        booking.setDistrict(district[position]);
//        booking.setGender(gender[position]);
//        System.out.println("district---------------------------------"+district[position]);
//        Toast.makeText(getApplicationContext(),
//                        gender[position],
//                        Toast.LENGTH_LONG)
//                .show();
//
//        Toast.makeText(getApplicationContext(),
//                        district[position],
//                        Toast.LENGTH_LONG)
//                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onLoad(int vid) {

        DBConnection dbConnection=new DBConnection();
        connection=dbConnection.getConnection();
        if (connection != null) {

            try {
                String query = "SELECT * FROM [slcrms].[dbo].[vehicle] WHERE id = ?";
                PreparedStatement statement = null;
                statement = connection.prepareStatement(query);
                statement.setInt(1, vid);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Vehicle vehicle =new Vehicle();
                    vehicle.setImage("https://imgd.aeplcdn.com/370x208/n/cw/ec/130591/fronx-exterior-right-front-three-quarter-4.jpeg?isig=0&q=75");
                    vehicle.setTitle(resultSet.getString(2));

                    System.out.println("============="+vehicle.getTitle());

                    if (vehicle.getImage() != null) {
                        Glide.with(imageViewVehicle.getContext())
                                .load(vehicle.getImage())
                                .into(imageViewVehicle);

                    }
                    collapsingToolbarLayout.setTitle(vehicle.getTitle());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                email= UserSingleton.getInstance().getUserEmail();
                String query = "SELECT * FROM [slcrms].[dbo].[user] WHERE email = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, email);

                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()){
                    UserHelperClass userHelperClass=new UserHelperClass();
                    userHelperClass.setRegName(resultSet.getString(2));
                    userHelperClass.setEmail(resultSet.getString(4));
                    userHelperClass.setUserName(resultSet.getString(6));
                    userHelperClass.setIdNumber(resultSet.getString(3));
                    userHelperClass.setPhonenNumber(resultSet.getString(5));
                    userHelperClass.setPassword(resultSet.getString(9));


                    name_txt.setText(userHelperClass.getRegName());
                    nic_txt.setText(userHelperClass.getIdNumber());
                    mobile_txt.setText(userHelperClass.getPhonenNumber());

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else {
            Context context = getApplicationContext();
            Toast.makeText(context, "Check the connection!", Toast.LENGTH_SHORT).show();
        }

    }

}