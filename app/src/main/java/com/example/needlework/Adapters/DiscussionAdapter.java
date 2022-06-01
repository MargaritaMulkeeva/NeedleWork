package com.example.needlework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.user.GetUserResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.DiscussionHolder> {

    public DiscussionAdapter(List<DiscussionsResponseBody> mDiscussions, Context context, OnAdapterItemClickListener listener, String token) {
        this.mDiscussions = mDiscussions;
        this.context = context;
        this.listener = listener;
        this.token = token;
    }

    public  class DiscussionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnAdapterItemClickListener listener;

        public TextView tvTheme;
        public TextView tv_nickname;
        public TextView tv_category;
        public TextView tvRating;
        public TextView tv_date;

        public DiscussionHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            tvTheme = itemView.findViewById(R.id.tvTheme);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_category = itemView.findViewById(R.id.tv_category);
            tvRating = itemView.findViewById(R.id.tvRating);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    private List<DiscussionsResponseBody> mDiscussions;
    private Context context;
    private OnAdapterItemClickListener listener;
    private ApiService service = ApiHandler.getmInstance().getService();
    String token;

    @NonNull
    @Override
    public DiscussionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_discussions, parent, false);
        return new DiscussionAdapter.DiscussionHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionHolder holder, int position) {
        final DiscussionsResponseBody discussions = mDiscussions.get(position);

        holder.tvTheme.setText(discussions.getTheme());
        holder.tvRating.setText(String.valueOf(discussions.getRating()));
        holder.tv_date.setText(discussions.getCreatedAt().substring(0, 10));

        service.getUser(token).enqueue(new Callback<GetUserResponseBody>() {
            @Override
            public void onResponse(Call<GetUserResponseBody> call, Response<GetUserResponseBody> response) {
                if (response.isSuccessful()){
                    holder.tv_nickname.setText(response.body().getUser().getNickName());
                }
            }

            @Override
            public void onFailure(Call<GetUserResponseBody> call, Throwable t) {

            }
        });

        service.getCategoriesOfDiscussion().enqueue(new Callback<List<CategoriesOfPatternResponseBody>>() {
            @Override
            public void onResponse(Call<List<CategoriesOfPatternResponseBody>> call, Response<List<CategoriesOfPatternResponseBody>> response) {
                for (int i = 0; i < response.body().size(); i++){
                    if(response.body().get(i).getId()==discussions.getCategoryOfDiscussionsId()){
                        holder.tv_category.setText(response.body().get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CategoriesOfPatternResponseBody>> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDiscussions.size();
    }
}
