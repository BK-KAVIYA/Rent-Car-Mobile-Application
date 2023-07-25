
package com.example.sourthenlankacarrental.Messages;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.Util.DateUtils;

public class SentMessageHolder extends BaseMessageViewHolder {
    TextView messageText, timeText,date;

    SentMessageHolder(View itemView) {
        super(itemView);

        messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
        timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);
        date = (TextView) itemView.findViewById(R.id.dateTextView);

    }

    public void bind(BaseMessage message) {
        messageText.setText(message.getMessage());
        timeText.setText(message.getTime());
        date.setText(message.getDate());

    }
}