package com.example.sourthenlankacarrental.BookingDetails;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sourthenlankacarrental.R;

public class CheckAvailability extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_availability);
        getSupportActionBar().hide();
    }
}
