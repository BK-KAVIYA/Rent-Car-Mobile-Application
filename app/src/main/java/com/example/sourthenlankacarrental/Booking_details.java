package com.example.sourthenlankacarrental;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.sourthenlankacarrental.BookingDetails.Booking;
import com.example.sourthenlankacarrental.BookingDetails.BookingSingleton;
import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.Guarantor.GaurantorDetails;
import com.example.sourthenlankacarrental.user.UserHelperClass;
import com.example.sourthenlankacarrental.user.UserSingleton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class Booking_details extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int PICK_IMAGE_REQUEST = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private byte[] selectedImageBytes;

    private byte[] selectedImageBytesSelfi;
    Connection connection;
    TextInputLayout start_date,end_date;
    TextInputEditText start_date_txt,end_date_txt,name_txt,mobile_txt,nic_txt,age_txt;
    ImageView imageViewVehicle,nicimage,imgselfi;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Spinner district_txt;
    Button booking_btn,open_camera;
    CheckBox driverStatusCheckbox;
    Booking booking=new Booking();


    long differenceInDays;
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

    BookingSingleton bookingDetails = BookingSingleton.getInstance();
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
        open_camera=findViewById(R.id.delfbtn);
        imgselfi=findViewById(R.id.imgcamera);

        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                } else {
                    Toast.makeText(Booking_details.this, "Camera not available.", Toast.LENGTH_SHORT).show();
                }
            }
        });





        start_date_txt.setText(bookingDetails.getFromDate());
        end_date_txt.setText(bookingDetails.getToDate());
        booking.setStartDate(bookingDetails.getFromDate());
        booking.setEndDate(bookingDetails.getToDate());

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

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-dd-M");

                // Parse the from date and to date strings into LocalDate objects
                String fromDateStr = booking.getStartDate();
                String toDateStr = booking.getEndDate();
                LocalDate fromDate = LocalDate.parse(fromDateStr, dateFormatter);
                LocalDate toDate = LocalDate.parse(toDateStr, dateFormatter);

                // Calculate the difference in days
                long differenceInDays = ChronoUnit.DAYS.between(fromDate, toDate);

                System.out.println("Difference in days: " + differenceInDays);

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

                LocalDate currentDate = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
                    String formattedDate = currentDate.format(formatter);
                    System.out.println("Date----------"+formattedDate);
                    booking.setBooking_date(formattedDate);
                }


                booking.setStatus(0);
                int driverStatus = driverStatusCheckbox.isChecked() ? 1 : 0;
                booking.setDriverStatus(driverStatus);
                booking.setUserEmail(UserSingleton.getInstance().getUserEmail());
                booking.setNic_url("people_1.png");
                booking.setVehicle_id(getIntent().getIntExtra("vid",1));
                byte[] imageData=null;
                if (selectedImageBytes != null) {
                    // Upload the image to the SQL Server
                    imageData = selectedImageBytes;
                } else {
                    Toast.makeText(Booking_details.this, "No image selected.", Toast.LENGTH_SHORT).show();
                    return;
                }
                byte[] imageDataselfi=null;
                if (selectedImageBytesSelfi != null) {
                    // Upload the image to the SQL Server
                    imageDataselfi = selectedImageBytesSelfi;
                } else {
                    Toast.makeText(Booking_details.this, "No image selected.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBConnection dbConnection=new DBConnection();
                connection=dbConnection.getConnection();
                if (connection != null) {

                    String query = "INSERT INTO [slcrms].[dbo].[booking] (customer_nic, customer_name, customer_email,customer_phone,vehicle_id,from_date,to_date,posting_date,district,selfi_image,nic_image,driver_status,gender,status,is_complete,is_deleted) VALUES (?,?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement statement = null;
                    try {
                        statement = connection.prepareStatement(query);
                        statement.setString(1, booking.getNic());
                        statement.setString(2, booking.getName());
                        statement.setString(3, booking.getUserEmail());
                        statement.setString(4, booking.getPhone());
                        statement.setInt(5, booking.getVehicle_id());
                        statement.setString(6, booking.getStartDate());
                        statement.setString(7, booking.getEndDate());
                        statement.setString(8, booking.getBooking_date());
                        statement.setString(9, booking.getDistrict());
                        statement.setBytes(10,imageDataselfi);
                        statement.setBytes(11,imageData);
                        statement.setInt(12, booking.getDriverStatus());
                        statement.setString(13, booking.getGender());
                        statement.setInt(14, booking.getStatus());
                        statement.setInt(15, booking.getIs_complete());
                        statement.setInt(16, booking.getIs_delete());


                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {

                            String query1 = "SELECT * FROM [slcrms].[dbo].[booking] WHERE vehicle_id = ? AND posting_date = ?";
                            PreparedStatement statement1 = connection.prepareStatement(query1);
                            statement1.setInt(1, booking.getVehicle_id());
                            statement1.setString(2, booking.getBooking_date());

                            ResultSet resultSet1 = statement1.executeQuery();

                            if (resultSet1.next()) {
                                int bookingId=resultSet1.getInt(1);
                                Intent intent=new Intent(Booking_details.this, GaurantorDetails.class);
                                intent.putExtra("BID", bookingId);
                                intent.putExtra("DDIF",differenceInDays);
                                intent.putExtra("VID",vehicleId);
                                startActivity(intent);

                                Context context = getApplicationContext();
                                Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();
                            }else{
                                Context context = getApplicationContext();
                                Toast.makeText(context, "Complete Previouse Step!", Toast.LENGTH_SHORT).show();
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



        nicimage=findViewById(R.id.nicgallery);

        Button btnGallery=findViewById(R.id.nicbtn);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openGallery();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                displaySelectedImage(bitmap);
                // Convert the bitmap to a byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                selectedImageBytes = baos.toByteArray();
                // Close the InputStream to release resources properly
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            displaySelectedImageId(imageBitmap);
            // Convert the bitmap to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            selectedImageBytesSelfi = baos.toByteArray();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    private void displaySelectedImage(Bitmap bitmap) {
        // Display the selected image in the ImageView
        nicimage.setImageBitmap(bitmap);
    }
    private void displaySelectedImageId(Bitmap bitmap) {
        // Display the selected image in the ImageView
        imgselfi.setImageBitmap(bitmap);
    }

//    private class InsertImageTask extends AsyncTask<byte[], Void, String> {
//        @Override
//        protected String doInBackground(byte[]... params) {
//            System.out.println("This method is call");
//            byte[] imageData = params[0];
//            Connection connection = null;
//            try {
//                // Get a connection from the DBConnectionHelper
//                connection = DBConnection.getConnection();
//
//                // Prepare the SQL statement
//                String sql = "INSERT INTO images (image_data) VALUES (?)";
//                PreparedStatement statement = connection.prepareStatement(sql);
//                statement.setBytes(1, imageData);
//
//                // Execute the SQL statement
//                int rowsAffected = statement.executeUpdate();
//
//                if (rowsAffected > 0) {
//                    return "Image uploaded successfully.";
//                } else {
//                    return "Failed to upload image.";
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return "Error: " + e.getMessage();
//            } finally {
//                // Close the connection using the DBConnectionHelper
//                // DBConnectionHelper.closeConnection(connection);
//            }
//        }

//        @Override
//        protected void onPostExecute(String result) {
//            // Handle the result after the upload is complete
//            Toast.makeText(Booking_details.this, result, Toast.LENGTH_SHORT).show();
//        }
//    }
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
                    vehicle.setTitle(resultSet.getString(2));
                    vehicle.setImage(resultSet.getString(5));

                    if (resultSet.getString(2) != null) {
                        Glide.with(imageViewVehicle.getContext()).load(Uri.parse(vehicle.getImage()))
                                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .error(R.drawable.avatar1)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }
                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }

                                }).into(imageViewVehicle);

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

                    bookingDetails.setCustomerName(resultSet.getString(2));
                    bookingDetails.setCustomerAddress(resultSet.getString(6));

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