package com.example.needlework.Lessons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.needlework.Adapters.CategoriesAdapter;
import com.example.needlework.Adapters.LessonAdapter;
import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.lessons.LessonResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.Patterns.ChoosePattern;
import com.example.needlework.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lessons extends AppCompatActivity {

    private LessonAdapter lessonAdapter;
    private List<LessonResponseBody> mLessons;
    private RecyclerView mLessonsContainer;

    public WebView video;

    ImageButton btn_back;

    private ApiService service = ApiHandler.getmInstance().getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        btn_back = findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lessons.this, MainActivity.class);
                startActivity(intent);
            }
        });

        video = findViewById(R.id.video);

        getLesson();
    }

    private void getLesson(){
        AsyncTask.execute(()->{
            service.getLessons().enqueue(new Callback<List<LessonResponseBody>>() {
                @Override
                public void onResponse(Call<List<LessonResponseBody>> call, Response<List<LessonResponseBody>> response) {
                    if(response.isSuccessful()){
                        video.getSettings().setJavaScriptEnabled(true);
                        video.loadUrl("https://www.youtube.com/channel/UCZUfidyA2b5k1fyWkPMWk1g");
                    }
                    else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(Lessons.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<List<LessonResponseBody>> call, Throwable t) {
                    new AlertDialog.Builder(Lessons.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }
}