package com.example.sourthenlankacarrental.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourthenlankacarrental.BookingDetails.MyBookingFragment;
import com.example.sourthenlankacarrental.GaurantorDetails;
import com.example.sourthenlankacarrental.R;

public class PaymentActivity extends AppCompatActivity {
    Button pay_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pyament_getway_layout);
        getSupportActionBar().hide();

        pay_button=findViewById(R.id.pay_button);
        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentActivity.this, MyBookingFragment.class);
                startActivity(intent);
            }
        });


    }
}
