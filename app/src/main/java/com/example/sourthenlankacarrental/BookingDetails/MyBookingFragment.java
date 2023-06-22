package com.example.sourthenlankacarrental.BookingDetails;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.R;


import java.util.ArrayList;
import java.util.List;

public class MyBookingFragment extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookingManager bookingManager;
    private List<Booking> bookingList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooking);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.booking_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookingList = new ArrayList<>();
        bookingList.add(new Booking("Range Rover", "23-06-2023", "25-06-2023", "Approve"));
        bookingList.add(new Booking("Range Rover", "25-06-2023", "28-06-2023", "Review"));
        bookingList.add(new Booking("Range Rover", "30-06-2023", "05-07-2023", "Review"));

        bookingManager = new BookingManager(this, bookingList);
        recyclerView.setAdapter(bookingManager);
    }


}
