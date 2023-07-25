package com.example.sourthenlankacarrental.Messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.BookingDetails.BookingViewHolder;
import com.example.sourthenlankacarrental.R;

import java.util.List;

public class MessageManager extends RecyclerView.Adapter<BaseMessageViewHolder> {

    Context context;
    List<BaseMessage> baseMessageList;

    int sentOrRecieved;

    private boolean isDateSet = false;

    private String previousDate = "";



    public MessageManager(Context context, List<BaseMessage> baseMessageList) {
        this.context = context;
        this.baseMessageList = baseMessageList;
    }

    @Override
    public int getItemViewType(int position) {
        BaseMessage currentMessage = baseMessageList.get(position);
        if (currentMessage.isSent()) {
            sentOrRecieved= 0; // Sent message
        } else {
            sentOrRecieved= 1; // Received message
        }
        return sentOrRecieved;
    }

    @NonNull
    @Override
    public BaseMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 0) {
            // Sent message layout
            View sentView = inflater.inflate(R.layout.send_message_layout, parent, false);
            return new SentMessageHolder(sentView);
        } else {
            // Received message layout
            View receivedView = inflater.inflate(R.layout.recieved_message_layout, parent, false);
            return new ReceivedMessageHolder(receivedView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMessageViewHolder holder, int position) {
        BaseMessage currentMessage = baseMessageList.get(position);

        holder.bind(currentMessage);

        // Check if the date needs to be set
        if (!isDateSet || !previousDate.equals(currentMessage.getDate())) {
            holder.date.setVisibility(View.VISIBLE);
            holder.date.setText(currentMessage.getDate());
            isDateSet = true;
            previousDate = currentMessage.getDate();
        } else {
            holder.date.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return baseMessageList.size();
    }



}
