package com.example.needlework.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.needlework.Lessons.Lessons;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.lessons.LessonResponseBody;
import com.example.needlework.R;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonHolder> {

    public LessonAdapter(List<LessonResponseBody> mLesson, Context context) {
        this.mLesson = mLesson;
        this.context = context;
    }

    public class LessonHolder extends RecyclerView.ViewHolder{

        public WebView video;

        public LessonHolder(@NonNull View itemView) {
            super(itemView);

            video = itemView.findViewById(R.id.video);
        }
    }

    private List<LessonResponseBody> mLesson;
    private Context context;

    @NonNull
    @Override
    public LessonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_lessons, parent, false);
        return new LessonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonHolder holder, int position) {
        final LessonResponseBody lesson = mLesson.get(position);

        holder.video.getSettings().setJavaScriptEnabled(true);
        holder.video.loadUrl(lesson.getLinkToVideo());
    }

    @Override
    public int getItemCount() {
        return mLesson.size();
    }
}
