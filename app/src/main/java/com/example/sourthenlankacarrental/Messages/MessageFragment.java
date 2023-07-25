
package com.example.sourthenlankacarrental.Messages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.sourthenlankacarrental.BookingDetails.MyBookingFragment;
import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.Payment.PaymentActivity;
import com.example.sourthenlankacarrental.R;


import com.example.sourthenlankacarrental.user.UserHelperClass;
import com.example.sourthenlankacarrental.user.UserSingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MessageFragment extends Fragment {
    Connection connection;

    String email;
    ImageButton SendMessage;

    final String message_to="Admin";
    final String user_type="customer";
    RecyclerView recyclerView;
    MessageManager messageManageradapter;
    EditText sendMessageText;

    ArrayList<BaseMessage> baseMessageslist=new ArrayList();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);


        DBConnection dbConnection=new DBConnection();
        connection=dbConnection.getConnection();

        recyclerView = view.findViewById(R.id.nessage_send_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sendMessageText=view.findViewById(R.id.send_message);
        SendMessage = view.findViewById(R.id.send_button);

        getPriviouseMessage();

        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(sendMessageText.getText())){
                    messageManageradapter=new MessageManager(getContext(),sendMessage());
                    recyclerView.setAdapter(messageManageradapter);
                    sendMessageText.setText("");
                }else{
                    Context context = getContext();
                    Toast.makeText(context, "Enter your Message first!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    return view;
    }

    public List<BaseMessage> sendMessage() {

        String messageText = sendMessageText.getText().toString();
        String senderEmail = UserSingleton.getInstance().getUserEmail();

        // Get current date and time
        LocalDateTime now = null;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;

        int year = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
            year = now.getYear();
            month = now.getMonthValue();
            day = now.getDayOfMonth();
            hour = now.getHour();
            minute = now.getMinute();
            int second = now.getSecond();
        }

        String date = year + "/" + month + "/" + day;
        String time = hour + ":" + minute;



        baseMessageslist.add(new BaseMessage(messageText, senderEmail, date, time));

        if (connection != null) {

            String query = "INSERT INTO [slcrms].[dbo].[customer_message] (user_email, message_to,message, received_time,received_date,is_replied,user_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(query);
                statement.setString(1, senderEmail);
                statement.setString(2, message_to);
                statement.setString(3, messageText);
                statement.setString(4, time);
                statement.setString(5, date);
                statement.setInt(6, 0);
                statement.setString(7, user_type);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    sendMessageText.setText("");
                } else {
                    Context context = getContext();
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        return baseMessageslist;
    }

    public void getPriviouseMessage(){


        email= UserSingleton.getInstance().getUserEmail();

        try {
            String query = "SELECT * FROM [slcrms].[dbo].[customer_message] WHERE user_email IN (?,'Admin')";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            baseMessageslist.clear();
            while(resultSet.next()){
                baseMessageslist.add(new BaseMessage(resultSet.getString(4),resultSet.getString(2),resultSet.getString(6),resultSet.getString(5)));
            }
            messageManageradapter=new MessageManager(getContext(),baseMessageslist);
            recyclerView.setAdapter(messageManageradapter);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



        }

