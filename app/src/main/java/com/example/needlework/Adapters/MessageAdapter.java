package com.example.needlework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.messages.MessageResponseBody;
import com.example.needlework.NetWork.Models.user.GetUserResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;
import com.example.needlework.common.Constants;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.PrimitiveIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ApiService service = ApiHandler.getmInstance().getService();

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
        holder.date.setText(messages.getCreatedAt().substring(0, 10));
        holder.message.setText(messages.getText());
        String token = context.getSharedPreferences(Constants.storageName, Context.MODE_PRIVATE).getString("token", "");

        service.getUser(token).enqueue(new Callback<GetUserResponseBody>() {
            @Override
            public void onResponse(Call<GetUserResponseBody> call, Response<GetUserResponseBody> response) {
                if (response.isSuccessful()){
                    Picasso.with(context)
                            .load(response.body().getUser().getAvatar())
                            .placeholder(R.drawable.avatar_placeholder)
                            .into(holder.photo);

                    holder.nickName.setText(response.body().getUser().getNickName());
                }
            }

            @Override
            public void onFailure(Call<GetUserResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }
}
