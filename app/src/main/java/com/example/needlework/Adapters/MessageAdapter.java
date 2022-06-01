package com.example.needlework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.messages.MessageResponseBody;
import com.example.needlework.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    public MessageAdapter(List<MessageResponseBody> mMessage, Context context) {
        this.mMessage = mMessage;
        this.context = context;
    }

    public class MessageHolder extends RecyclerView.ViewHolder{

        public TextView nickName;
        public TextView date;
        public TextView message;
        public ImageView photo;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            nickName = itemView.findViewById(R.id.tv_nickname);
            date = itemView.findViewById(R.id.tv_date);
            message = itemView.findViewById(R.id.tv_message);
            photo = itemView.findViewById(R.id.photo);
        }
    }

    private List<MessageResponseBody> mMessage;
    private Context context;

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_messages, parent, false);
        return new MessageAdapter.MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        final MessageResponseBody messages = mMessage.get(position);

        holder.nickName.setText(String.valueOf(messages.getUserId()));
        //SimpleDateFormat dt1 = new SimpleDateFormat("dd.mm.yyyy");
        holder.date.setText(messages.getCreatedAt());
        holder.message.setText(messages.getText());
    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }
}
