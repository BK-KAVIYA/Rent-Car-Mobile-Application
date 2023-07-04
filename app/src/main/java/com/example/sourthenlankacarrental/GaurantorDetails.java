package com.example.sourthenlankacarrental;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.Guarantor.Guarantor;
import com.example.sourthenlankacarrental.Payment.PaymentActivity;
import com.example.sourthenlankacarrental.calculate.PriceCalculator;
import com.example.sourthenlankacarrental.user.UserSingleton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GaurantorDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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
        //checkBox=findViewById(R.id.checkbox);
        layout=findViewById(R.id.relativelayout);


        //booking_btn.setEnabled(false);

        // Set a listener to track checkbox state changes
//        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            // Enable the button if the checkbox is checked
//            booking_btn.setEnabled(isChecked);
//        });
        booking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatepopUpwindow();
            }
        });


//        booking_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                guarantor.setReserved_id(BookingId);
//                guarantor.setName(name_txt.getText().toString());
//                guarantor.setNic(nic_txt.getText().toString());
//                guarantor.setPhone(mobile_txt.getText().toString());
//                guarantor.setAge(Integer.parseInt(age_txt.getText().toString()));
//                guarantor.setNic_url("gaura_1.jpg");
//
//                DBConnection dbConnection=new DBConnection();
//                connection=dbConnection.getConnection();
//                if (connection != null) {
//
//                    String query = "INSERT INTO [slcrms].[dbo].[guarantor] (reserved_id, name, nic,phone,age,district,gender,nic_copy,is_deleted) VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
//                    PreparedStatement statement = null;
//                    try {
//                        statement = connection.prepareStatement(query);
//                        statement.setInt(1, guarantor.getReserved_id());
//                        statement.setString(2, guarantor.getName());
//                        statement.setString(3, guarantor.getNic());
//                        statement.setString(4, guarantor.getPhone());
//                        statement.setInt(5, guarantor.getAge());
//                        statement.setString(6, guarantor.getDistrict());
//                        statement.setString(7, guarantor.getGender());
//                        statement.setString(8, guarantor.getNic_url());
//                        statement.setInt(9, 0);
//
//                        int rowsAffected = statement.executeUpdate();
//                        if (rowsAffected > 0) {
//
//                            int bookingId=guarantor.getReserved_id();
//                            Intent intent=new Intent(GaurantorDetails.this, PaymentActivity.class);
//                            intent.putExtra("BID", bookingId);
//                            startActivity(intent);
//
//                            Context context = getApplicationContext();
//                            Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();
//
//
//                        } else {
//                            Context context = getApplicationContext();
//                            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                }
//
//            }
//        });
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
                        statement.setString(8, guarantor.getNic_url());
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
