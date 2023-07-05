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

public class MessageManager extends RecyclerView.Adapter<SentMessageHolder> {

    Context context;
    List<BaseMessage> baseMessageList;

    private boolean isDateSet = false;

    private String previousDate = "";

    public MessageManager(Context context, List<BaseMessage> baseMessageList) {
        this.context = context;
        this.baseMessageList = baseMessageList;
    }

    @NonNull
    @Override
    public SentMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SentMessageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.send_message_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SentMessageHolder holder, int position) {

        BaseMessage currentMessage = baseMessageList.get(position);

        // Check if the date needs to be set
        if (!isDateSet || !previousDate.equals(currentMessage.getDate())) {
            holder.date.setVisibility(View.VISIBLE);
            holder.date.setText(currentMessage.getDate());
            isDateSet = true;
            previousDate = currentMessage.getDate();
        } else {
            holder.date.setVisibility(View.GONE);
        }

        holder.messageText.setText(currentMessage.getMessage());
        holder.timeText.setText(currentMessage.getTime());

    }

    @Override
    public int getItemCount() {
        return baseMessageList.size();
    }
}
