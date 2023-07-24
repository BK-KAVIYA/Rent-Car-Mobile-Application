package com.example.sourthenlankacarrental.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.R;

import java.util.List;

public class NotificationManager extends RecyclerView.Adapter<NotificationViewHolder> {

    Context context;
    List<Notification> notificationList;

    public NotificationManager(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.title.setText(notificationList.get(position).getTitle());
        holder.message.setText(notificationList.get(position).getMessage());
        holder.date.setText(String.valueOf(notificationList.get(position).getDate()));
        holder.time.setText(String.valueOf(notificationList.get(position).getTime()));


        // holder.imageView.setImageResource(notificationList.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
