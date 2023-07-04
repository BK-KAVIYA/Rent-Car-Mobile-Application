package com.example.sourthenlankacarrental.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourthenlankacarrental.BookingDetails.MyBookingFragment;
import com.example.sourthenlankacarrental.GaurantorDetails;
import com.example.sourthenlankacarrental.R;

public class PaymentActivity extends AppCompatActivity {
    Button pay_button;

    EditText amount,card_number,expire_date,cvv;

    int BookingId;
    double price;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pyament_getway_layout);
        getSupportActionBar().hide();

        Intent intent=getIntent();
        if (intent != null) {
            // Retrieve the saved values from the bundle
            BookingId = getIntent().getIntExtra("BID",10);
            price = getIntent().getIntExtra("Price",1000);
        }
        System.out.println("price------------------"+price);

        pay_button=findViewById(R.id.pay_button);
        amount =findViewById(R.id.payment_amount_edittext);
        card_number =findViewById(R.id.card_number_edittext);
        expire_date =findViewById(R.id.expiry_date_edittext);
        cvv =findViewById(R.id.cvv_edittext);

        amount.setText((int) price);
        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentActivity.this, MyBookingFragment.class);
                startActivity(intent);
            }
        });


    }
}
