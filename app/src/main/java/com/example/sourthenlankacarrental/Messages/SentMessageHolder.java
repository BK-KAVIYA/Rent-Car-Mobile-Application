package com.example.sourthenlankacarrental.Messages;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.Util.DateUtils;

public class SentMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText,date;

    SentMessageHolder(View itemView) {
        super(itemView);

        messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
        timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);
        date = (TextView) itemView.findViewById(R.id.text_gchat_date_me);

    }

    void bind(BaseMessage message) {
        messageText.setText(message.getMessage());

        // Format the stored timestamp into a readable String using method.
       // timeText.setText(DateUtils.formatDateTime(message.getCreatedAt()));
    }
}
