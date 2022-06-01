package com.example.needlework.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternResponseBody;
import com.example.needlework.NetWork.Models.userBookmarks.UserBookmarksResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkHolder> {

    public BookmarkAdapter(List<UserBookmarksResponseBody> mBokkmarks, Context context, OnAdapterItemClickListener listener) {
        this.mBokkmarks = mBokkmarks;
        this.context = context;
        this.listener = listener;
    }

    public class BookmarkHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tv_name;
        public TextView tv_descr;
        public ImageView image;
        public ImageButton imgDelete;

        OnAdapterItemClickListener listener;

        public BookmarkHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tvName);
            tv_descr = itemView.findViewById(R.id.text_descriprion);
            image = itemView.findViewById(R.id.img_photo);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    private List<UserBookmarksResponseBody> mBokkmarks;
    private Context context;
    private OnAdapterItemClickListener listener;

    private ApiService service = ApiHandler.getmInstance().getService();



    @NonNull
    @Override
    public BookmarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_book_marks, parent, false);
        return new BookmarkAdapter.BookmarkHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkHolder holder, int position) {
        final UserBookmarksResponseBody pattern = mBokkmarks.get(position);

        service.getKnittingPattern(pattern.getKnittingPatternId()).enqueue(new Callback<KnittingPatternResponseBody>() {
            @Override
            public void onResponse(Call<KnittingPatternResponseBody> call, Response<KnittingPatternResponseBody> response) {
                if(response.isSuccessful()){
                    holder.tv_name.setText(response.body().getName());
                    holder.tv_descr.setText(response.body().getDescription());
                    Picasso.with(context)
                            .load(response.body().getImageOfProduct()).into(holder.image);
                }
                else{
                    String message = ErrorUtils.error(response).getError();
                    new AlertDialog.Builder(context).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            }

            @Override
            public void onFailure(Call<KnittingPatternResponseBody> call, Throwable t) {
                new AlertDialog.Builder(context).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBokkmarks.size();
    }
}
