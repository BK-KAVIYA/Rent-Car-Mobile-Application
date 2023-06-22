package com.example.sourthenlankacarrental.Messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.BookingDetails.BookingViewHolder;
import com.example.sourthenlankacarrental.R;

import java.util.List;

public class MessageManager extends RecyclerView.Adapter<SentMessageHolder> {

    Context context;
    List<BaseMessage> baseMessageList;

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
        holder.date.setText(baseMessageList.get(position).getDate());
        holder.messageText.setText(baseMessageList.get(position).getMessage());
        holder.timeText.setText(baseMessageList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return baseMessageList.size();
    }
}
