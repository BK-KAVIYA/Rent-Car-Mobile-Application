package com.example.sourthenlankacarrental.BookingDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.notification.NotificationViewHolder;

import java.util.List;

public class BookingManager extends RecyclerView.Adapter<BookingViewHolder> {
    Context context;
    List<Booking> bookingList;

    public BookingManager(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_details_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        holder.title.setText(bookingList.get(position).getTitle());
        holder.startDate.setText(bookingList.get(position).getStartDate());
        holder.endDate.setText(String.valueOf(bookingList.get(position).getEndDate()));
        String status;
        if(bookingList.get(position).getStatus()==0){
            status="Under Review";
        }else{
            status="Approved";
        }
        holder.status.setText(status);

        String imageUrl = bookingList.get(position).getVehicle_imgUrl();

        if (imageUrl != null) {
            Glide.with(holder.imageView.getContext())
                    .load(imageUrl)
                    .into(holder.imageView);
        }


    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
