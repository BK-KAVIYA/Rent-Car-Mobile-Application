package com.example.sourthenlankacarrental.notification;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.Messages.BaseMessage;
import com.example.sourthenlankacarrental.Messages.MessageManager;
import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.user.UserSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationManager adapter;
    private List<Notification> dataList;

    ArrayList<Notification> notifications=new ArrayList();

    Connection connection;

    String email;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        DBConnection dbConnection=new DBConnection();
        connection=dbConnection.getConnection();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }
        // showNotification("Sample Title","Sample Description");
        recyclerView = view.findViewById(R.id.notif_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        dataList = new ArrayList<>();
//        dataList.add(new Notification("Vehicle Booking Confirmation", "Booking has been successfully confirmed.", 4, 12.12,"2022-07-07"));
//        dataList.add(new Notification("Vehicle Return", "Thank you for returning your rental vehicle", 4, 12.14,"2022-07-07"));
//        dataList.add(new Notification("Payment Confirmation", "Payment has been successfully processed. ", 4, 15.23,"2022-07-07"));
//        dataList.add(new Notification("Vehicle Booking Confirmation", "Booking has been successfully confirmed.", 4, 17.35,"2022-07-07"));
//
//        // Add more items as needed
//
//        adapter = new NotificationManager(getContext(),dataList);
//        recyclerView.setAdapter(adapter);

        getPriviouseNotification();

        return view;
    }
    public void showNotification(String title,String context){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getContext(),"My Notification");
        builder.setContentTitle(title);
        builder.setContentText(context);
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent=new Intent(getContext(),NotificationFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(getContext(), 0,intent,PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        //Context context = getContext();

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=notificationManager.getNotificationChannel("My Notification");
            if(notificationChannel==null){
                NotificationChannel channel=new NotificationChannel("My Notification","My Notification", android.app.NotificationManager.IMPORTANCE_DEFAULT);
                channel.setLightColor(Color.GREEN);
                channel.enableVibration(true);
                notificationManager.createNotificationChannel(channel);
            }

        }
        notificationManager.notify(0,builder.build());


    }
    public void getPriviouseNotification() {


        email = UserSingleton.getInstance().getUserEmail();

        try {
            String query = "SELECT * FROM [slcrms].[dbo].[customer_notification] WHERE user_email=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            notifications.clear();
            while (resultSet.next()) {
                notifications.add(new Notification(resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7)));
            }
            adapter = new NotificationManager(getContext(), notifications);
            recyclerView.setAdapter(adapter);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
