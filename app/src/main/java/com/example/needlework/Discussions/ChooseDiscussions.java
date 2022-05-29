package com.example.needlework.Discussions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;
import com.example.needlework.secondFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseDiscussions extends AppCompatActivity {

    private long selectedDiscussionId;

    private TextView discussionText;

    private ApiService service = ApiHandler.getmInstance().getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_discussions);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            selectedDiscussionId = args.getLong("discussionId");
        }

        discussionText = findViewById(R.id.discussion_text);
        getSelectedDiscussion();

        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseDiscussions.this, MainActivity.class));
            }
        });

        LinearLayout lv = findViewById(R.id.lv_message);
        lv.bringToFront();
    }

    private void getSelectedDiscussion() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.getDiscussionsById(selectedDiscussionId).enqueue(new Callback<DiscussionsResponseBody>() {
                    @Override
                    public void onResponse(Call<DiscussionsResponseBody> call, Response<DiscussionsResponseBody> response) {
                        if (response.isSuccessful()) {
                            discussionText.setText(response.body().getTextOfDiscussions());
                        } else if (response.code() == 400) {
                            String error = ErrorUtils.error(response).getError();
                            Toast.makeText(ChooseDiscussions.this, error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChooseDiscussions.this, "Не удалось вывести категории схем", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DiscussionsResponseBody> call, Throwable t) {
                        Toast.makeText(ChooseDiscussions.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}