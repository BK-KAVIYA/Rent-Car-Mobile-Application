package com.example.sourthenlankacarrental.BookingDetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.sourthenlankacarrental.Booking_details;
import com.example.sourthenlankacarrental.DynamicItemList;
import com.example.sourthenlankacarrental.DynamicRVAdapter;
import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.vehicale.VehicleDetails;
import com.example.sourthenlankacarrental.vehicale.VehicleDetailsShow;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
public class DynamicRVAdapter1 extends RecyclerView.Adapter<DynamicRVAdapter1.ViewHolder> {
    ArrayList<DynamicItemList> dynamicRVModels;
    Booking_details booking_details;

    public DynamicRVAdapter1(ArrayList<DynamicItemList> dynamicRVModels) {
        this.dynamicRVModels = dynamicRVModels;
    }

    @Override
    public DynamicRVAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_rv_item_layout,parent,false);
        return new DynamicRVAdapter1.ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull DynamicRVAdapter1.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.carName.setText(dynamicRVModels.get(position).getTitle());
        holder.carDetails.setText(dynamicRVModels.get(position).getDescription());
        holder.ratingBar.setRating(dynamicRVModels.get(position).getReview());
        int vId=dynamicRVModels.get(position).getId();


        String imageUrl = dynamicRVModels.get(position).getImage();


//        Bitmap bitmap = BitmapFactory.decodeFile(imageUrl);
//        holder.imageView.setImageBitmap(bitmap);
        if (imageUrl != null) {
            Glide.with(holder.imageView.getContext())
                    .load(imageUrl)
                    .into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Handle item click here
                // Start the new activity
                Context context = view.getContext();
                Intent intent = new Intent(context, VehicleDetails.class);
                // Pass any extra data to the new activity if needed
                intent.putExtra("vid", vId);
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return dynamicRVModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView carName,carDetails;
        public ImageView imageView;

        public RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carName=itemView.findViewById(R.id.rvi_name);
            carDetails=itemView.findViewById(R.id.rvi_details);
            imageView=itemView.findViewById(R.id.rvi_image);
            ratingBar=itemView.findViewById(R.id.rvi_rating);

        }
    }



}
