package com.example.sourthenlankacarrental.Messages;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.Messages.BaseMessage;
import com.example.sourthenlankacarrental.R;

public abstract class BaseMessageViewHolder extends RecyclerView.ViewHolder {
    public TextView date;
    public BaseMessageViewHolder(View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.dateTextView);
    }

    public abstract void bind(BaseMessage currentMessage);
}
