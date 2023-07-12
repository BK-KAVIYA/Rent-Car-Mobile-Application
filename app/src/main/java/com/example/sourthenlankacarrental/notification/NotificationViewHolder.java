package com.example.sourthenlankacarrental.notification;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView title,message,time,date;
    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageViewIcon);
        title=itemView.findViewById(R.id.textViewTitle);
        message=itemView.findViewById(R.id.textViewMessage);
        time=itemView.findViewById(R.id.notificationDate);
        date=itemView.findViewById(R.id.textViewDate);
    }
}
