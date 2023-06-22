package com.example.sourthenlankacarrental;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourthenlankacarrental.Guarantor.Guarantor;
import com.example.sourthenlankacarrental.Payment.PaymentActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class GaurantorDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Guarantor guarantor=new Guarantor();
    String[] gender={"Select Gender","Male","Female"};
    String[] district={"Select district","Colombo","Gampaha","Kalutara","Kandy","Matale","Nuwara Eliya","Galle","Matara","Hambantota","Jaffna","Kilinochchi","Mannar","Vavuniya","Mullaitivu","Batticaloa","Ampara","Trincomalee","Kurunegala","Puttalam","Anuradhapura","Polonnaruwa","Badulla","Moneragala","Ratnapura","Kegalle"};

    FirebaseDatabase fdatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = fdatabase.getReference("guarantor_details");
    TextInputLayout start_date,end_date;
    TextInputEditText start_date_txt,end_date_txt,name_txt,mobile_txt,nic_txt,age_txt;
    ImageView imageViewVehicle,nicimage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView termsAndCondition;
    Spinner district_txt;
    Button booking_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaurantor_details);
        getSupportActionBar().hide();

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

        termsAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext(); // Get the context from the clicked view

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.popup_user_agreement);

                TextView agreementTextView = dialog.findViewById(R.id.agreement_text);

                // Customize the user agreement text based on your requirements
                String userAgreement = "**Customer Vehicle Booking Agreement**<\n" +
                        "\n" +
                        "01.I see that the vehicle bearing the above number which I rented from SOUTHERN LANKA RENT A CAR AND TOURS can only be used for regular purposes.  I declare that I will take full responsibility if this vehicle is used for any illegal activities.  If the car is used for an illegal purpose during the time it is in my custody or is taken into the custody of any police or court due to an accident or drunk driving or any other reason, to pay the daily fees agreed with the company for the entire period of such possession.  I agree.\n" +
                        "\n" +
                        "02. That I will drive the car bearing the above number and that I will be liable for all damages and compensations that may arise from allowing another party to drive it.  (It is not related with car insurance.)\n" +
                        "\n" +
                        "03. I agree to maintain the above mentioned vehicle properly (check engine oil, brake oil radiator water and accessories) and use it carefully and check all documents such as income license, insurance certificate etc. of the vehicle.\n" +
                        "\n" +
                        "04. I agree to pay for any loss or damage that occurs while in my possession of the said vehicle from my money or insurance.  (For manual vehicles, I have to bear the damage caused by driving the clutch plate due to driving with my foot on the clutch, and the damage that may be caused by continuing to drive even though any signal indicating a fault in the vehicle is on.\n" +
                        "\n" +
                        "05. The agency will charge a daily tax for the days that the new owner is working on such repairs.  I agree to pay the agreed daily tax amount for each day it increases.\n" +
                        "\n" +
                        "06. I agree to pay the amount due to the company if the said car meets with an accident while in my possession or if the vehicle has to be loaded or loaded in the event of a break down.\n" +
                        "\n" +
                        " 07. I understand that the institution will not provide the deposit until the amount due for loss or any other loss caused by an accident is assessed by the institution.  I agree to any course of action taken by the institution.\n" +
                        "\n" +
                        " 08. I understand that I will not be able to keep the vehicle in my possession beyond the agreed number of days after taking delivery of the car.  I agree to pay an amount of Rs 800.00 for every hour of delay in keeping it so.  A fine of Rs\n" +
                        "\n" +
                        " 09. After receiving the car, in addition to the number of days agreed upon, without informing the company or in the company\n" +
                        " The company took possession of the said car without my consent\n" +
                        " I agree to any legal action\n" +
                        "\n" +
                        " 10. I agree that the above car will not be re-rented, pawned or given to any third party for any reason whatsoever.\n" +
                        "\n" +
                        " 11. A deposit of Rs.  But I know that a reasonable amount can be deducted from that amount for the damages to the vehicle due to my negligence.\n" +
                        "\n" +
                        " 12. If the above car gets into an accident due to my drunkenness, negligence or similar actions, or if it is destroyed by a thief, hostile kidnapping, terrorist act, I agree to pay the value of the car at the time I acquired the car and that amount will be paid to me. \n" +
                        "\n" +
                        " 13. I warrant that the two guarantors who have signed on my behalf for this agreement shall also be subject to the terms of this agreement and will be equally liable on my behalf and will appear on my behalf at any time.\n" +
                        "\n";
                agreementTextView.setText(userAgreement);

                dialog.show();
            }
        });




        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String bkUser_Email = currentUser.getEmail();

        booking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookingKey = myRef.push().getKey();
                guarantor.setName(name_txt.getText().toString());
                guarantor.setPhone(mobile_txt.getText().toString());
                guarantor.setNic(nic_txt.getText().toString());
                guarantor.setAge(age_txt.getText().toString());
                guarantor.setCustomer_email(bkUser_Email);
                guarantor.setId(bookingKey);


                Map<String, Object> bookingData = new HashMap<>();
                bookingData.put("name", guarantor.getName());
                bookingData.put("phone", guarantor.getPhone());
                bookingData.put("nic", guarantor.getNic());
                bookingData.put("gender", guarantor.getGender());
                bookingData.put("age", guarantor.getAge());
                bookingData.put("district", guarantor.getDistrict());
                bookingData.put("id",guarantor.getId());
                bookingData.put("customer_email",guarantor.getCustomer_email());

                myRef.child(guarantor.getId()).setValue(bookingData);

                myRef.setValue(bookingData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data saved successfully
                                Intent intent=new Intent(GaurantorDetails.this, PaymentActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while saving data
                                System.out.println("data insertion error");
                            }
                        });
            }
        });
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
}
