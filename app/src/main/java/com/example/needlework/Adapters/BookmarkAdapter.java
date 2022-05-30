package com.example.needlework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternResponseBody;
import com.example.needlework.NetWork.Models.userBookmarks.UserBookmarksResponseBody;
import com.example.needlework.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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

        OnAdapterItemClickListener listener;

        public BookmarkHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tvName);
            tv_descr = itemView.findViewById(R.id.text_descriprion);
            image = itemView.findViewById(R.id.img_photo);

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

//        Picasso.with(context)
//                .load(pattern.get()).into(holder.img_photo);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
