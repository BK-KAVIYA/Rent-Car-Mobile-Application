package com.example.sourthenlankacarrental.BookingDetails;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.R;

public class BookingViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView title,startDate,endDate,status;
    public BookingViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageViewIcon);
        title=itemView.findViewById(R.id.textViewTitle);
        startDate=itemView.findViewById(R.id.booking_datee);
        endDate=itemView.findViewById(R.id.booking_date_ends);
        status=itemView.findViewById(R.id.status);
    }
}
