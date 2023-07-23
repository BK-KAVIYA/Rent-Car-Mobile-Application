package com.example.sourthenlankacarrental.Guarantor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourthenlankacarrental.Booking_details;
import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.Payment.PaymentActivity;
import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.calculate.PriceCalculator;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GaurantorDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int PICK_IMAGE_REQUEST = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private byte[] selectedImageBytes;
    Guarantor guarantor=new Guarantor();
    String[] gender={"Select Gender","Male","Female"};
    String[] district={"Select district","Colombo","Gampaha","Kalutara","Kandy","Matale","Nuwara Eliya","Galle","Matara","Hambantota","Jaffna","Kilinochchi","Mannar","Vavuniya","Mullaitivu","Batticaloa","Ampara","Trincomalee","Kurunegala","Puttalam","Anuradhapura","Polonnaruwa","Badulla","Moneragala","Ratnapura","Kegalle"};
    Connection connection;
    TextInputEditText name_txt,mobile_txt,nic_txt,age_txt;
    ImageView imageViewVehicle,nicimage;
    TextView termsAndCondition;
    double totalPrice;
    CheckBox checkBox;
    Spinner district_txt;
    Button booking_btn;
    int BookingId,vehicle_ID;
    long Date_diff;
    RelativeLayout layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaurantor_details);
        getSupportActionBar().hide();

        Intent intent=getIntent();
        if (intent != null) {
            // Retrieve the saved values from the bundle
            BookingId = getIntent().getIntExtra("BID",10);
            Date_diff = getIntent().getLongExtra("DDIF",10);
            vehicle_ID = getIntent().getIntExtra("VID",10);
        }
        PriceCalculator priceCalculator=new PriceCalculator(Date_diff,vehicle_ID);
        totalPrice =priceCalculator.getTotalPrice();

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


        booking_btn=findViewById(R.id.book_btn);
        district_txt=findViewById(R.id.district);
        name_txt=findViewById(R.id.name_txt);
        mobile_txt=findViewById(R.id.mobile_txt);
        nic_txt=findViewById(R.id.nic_txt);
        age_txt=findViewById(R.id.age_txt);
        termsAndCondition=findViewById(R.id.termsandcondition);
        layout=findViewById(R.id.relativelayout);
        nicimage=findViewById(R.id.nicgallery);

        Button btnGallery=findViewById(R.id.nicbtn);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        booking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatepopUpwindow();
            }
        });



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
            displaySelectedImage(imageBitmap);
            // Convert the bitmap to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            selectedImageBytes = baos.toByteArray();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        if (parent.getId() == R.id.district) {
            // Handle Spinner 1 selection
            String selectedDistrict = district[position];
            guarantor.setDistrict(selectedDistrict);
        } else if (parent.getId() == R.id.gender) {
            // Handle Spinner 2 selection
            String selectedGender = gender[position];
            guarantor.setGender(selectedGender);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void CreatepopUpwindow(){
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView=inflater.inflate(R.layout.mainpopup,null);

        int width= ViewGroup.LayoutParams.MATCH_PARENT;
        int height=ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable=true;
        PopupWindow popupWindow=new PopupWindow(popUpView,width,height,focusable);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.BOTTOM,0,0);

            }
        });
        TextView Skip ,Gotit;
        Skip=popUpView.findViewById(R.id.Skip);
        Gotit=popUpView.findViewById(R.id.Gotit);
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        Gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guarantor.setReserved_id(BookingId);
                guarantor.setName(name_txt.getText().toString());
                guarantor.setNic(nic_txt.getText().toString());
                guarantor.setPhone(mobile_txt.getText().toString());
                guarantor.setAge(Integer.parseInt(age_txt.getText().toString()));
                guarantor.setNic_url("gaura_1.jpg");
                byte[] imageData=null;
                if (selectedImageBytes != null) {
                    // Upload the image to the SQL Server
                    imageData = selectedImageBytes;
                } else {
                    Toast.makeText(GaurantorDetails.this, "No image selected.", Toast.LENGTH_SHORT).show();
                    return;
                }
                DBConnection dbConnection=new DBConnection();
                connection=dbConnection.getConnection();
                if (connection != null) {

                    String query = "INSERT INTO [slcrms].[dbo].[guarantor] (reserved_id, name, nic,phone,age,district,gender,nic_copy,is_deleted) VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
                    PreparedStatement statement = null;
                    try {
                        statement = connection.prepareStatement(query);
                        statement.setInt(1, guarantor.getReserved_id());
                        statement.setString(2, guarantor.getName());
                        statement.setString(3, guarantor.getNic());
                        statement.setString(4, guarantor.getPhone());
                        statement.setInt(5, guarantor.getAge());
                        statement.setString(6, guarantor.getDistrict());
                        statement.setString(7, guarantor.getGender());
                        statement.setBytes(8, imageData);
                        statement.setInt(9, 0);

                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {

                            int bookingId=guarantor.getReserved_id();
                            Intent intent=new Intent(GaurantorDetails.this, PaymentActivity.class);
                            intent.putExtra("BID", bookingId);
                            intent.putExtra("Price",totalPrice);
                            startActivity(intent);

                            Context context = getApplicationContext();
                            Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();


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
        // and if you want to close popup when touch Screen
        popUpView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


}
