
package com.example.sourthenlankacarrental.Messages;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sourthenlankacarrental.R;
import com.example.sourthenlankacarrental.Util.DateUtils;
import com.example.sourthenlankacarrental.Util.ImageUtils;

public class ReceivedMessageHolder extends BaseMessageViewHolder {
    TextView messageText, timeText, nameText,dateText;
    ImageView profileImage;

    ReceivedMessageHolder(View itemView) {
        super(itemView);
        nameText = (TextView) itemView.findViewById(R.id.text_gchat_user_other);
        timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_other);
        dateText = (TextView) itemView.findViewById(R.id.dateTextView);
        messageText  = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
        profileImage = (ImageView) itemView.findViewById(R.id.image_gchat_profile_other);

    }

    public void bind(BaseMessage message) {
        messageText.setText(message.getMessage());
        nameText.setText("Customer agent");
        dateText.setText(message.getDate());
        timeText.setText(message.getTime());


    }
}

