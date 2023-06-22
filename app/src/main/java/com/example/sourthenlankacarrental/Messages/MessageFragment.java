package com.example.sourthenlankacarrental.Messages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.sourthenlankacarrental.R;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MessageFragment extends Fragment {
 ImageButton SendMessage;
    RecyclerView recyclerView;
    MessageManager messageManageradapter;
    EditText sendMessageText;

    ArrayList<BaseMessage> baseMessageslist=new ArrayList();

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        databaseReference = database.getReference();

        recyclerView = view.findViewById(R.id.nessage_send_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sendMessageText=view.findViewById(R.id.send_message);
        SendMessage = view.findViewById(R.id.send_button);

        getPriviouseMessage();

        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageManageradapter=new MessageManager(getContext(),sendMessage());
                recyclerView.setAdapter(messageManageradapter);
                sendMessageText.setText("");
            }
        });

    return view;
    }

    public List<BaseMessage> sendMessage() {
        System.out.println("call------------------------------------------------");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = firebaseDatabase.getReference("messages");

        String messageText = sendMessageText.getText().toString();
        String senderEmail = currentUser.getEmail();

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


        String messageId = messagesRef.push().getKey();
        //List<BaseMessage> baseMessageList=new ArrayList<>();;
        baseMessageslist.add(new BaseMessage(messageText, senderEmail, date, time));

        for (BaseMessage message : baseMessageslist) {
            if (messagesRef.child(messageId).setValue(message).isSuccessful()) {
                sendMessageText.setText("");
            }
        }


        return baseMessageslist;
    }

    public void getPriviouseMessage(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference messagesRef = firebaseDatabase.getReference("messages");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String senderEmail = currentUser.getEmail();


        Query query = databaseReference.child("messages").orderByChild("senderEmail").equalTo(senderEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    List<BaseMessage> baseMessageList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        BaseMessage baseMessage = snapshot.getValue(BaseMessage.class);
                        baseMessageList.add(baseMessage);
                    }

                    for (BaseMessage baseMessage : baseMessageList) {
                        System.out.println(baseMessage.getMessage()+"=====================");
                        baseMessageslist.add(new BaseMessage(baseMessage.getMessage(),baseMessage.getSenderEmail(),baseMessage.getDate(),baseMessage.getTime()));
                       // itemVehicle.add(new DynamicItemList(vehicle.getId(),vehicle.getTitle(), vehicleList.getDescription(), vehicleList.getRating(), vehicle.getImage()));
                    }
                    messageManageradapter=new MessageManager(getContext(),baseMessageslist);
                    recyclerView.setAdapter(messageManageradapter);
                    //messageManageradapter.notifyDataSetChanged();
                }
                // Use the vehicle object here
                else {
                    // Handle case when data doesn't exist
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            });
      //  return list;
    }



        }