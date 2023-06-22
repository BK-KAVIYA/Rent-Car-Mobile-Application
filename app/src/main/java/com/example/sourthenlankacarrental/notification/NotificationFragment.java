package com.example.sourthenlankacarrental.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationManager adapter;
    private List<Notification> dataList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.notif_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataList = new ArrayList<>();
        dataList.add(new Notification("Vehicle Booking Confirmation", "Booking has been successfully confirmed.", 4, 12.12));
        dataList.add(new Notification("Vehicle Return", "Thank you for returning your rental vehicle", 4, 12.20));
        dataList.add(new Notification("Payment Confirmation", "Payment has been successfully processed. ", 4, 15.20));
        dataList.add(new Notification("Vehicle Booking Confirmation", "Booking has been successfully confirmed.", 4, 17.30));

        // Add more items as needed

        adapter = new NotificationManager(getContext(),dataList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
