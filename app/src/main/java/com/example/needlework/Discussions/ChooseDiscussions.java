package com.example.needlework.Discussions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.needlework.Adapters.CategoriesAdapter;
import com.example.needlework.Adapters.MessageAdapter;
import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.messages.GetMessageByDiscussionResponseBody;
import com.example.needlework.NetWork.Models.messages.MessageResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.Patterns.ChoosePattern;
import com.example.needlework.R;
import com.example.needlework.secondFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseDiscussions extends AppCompatActivity {

    private long selectedDiscussionId;

    private TextView discussionText;

    private SharedPreferences sharedPreferences;

    private MessageAdapter messageAdapter;
    private List<MessageResponseBody> mMessages;
    private RecyclerView mMessageContainer;

    private ApiService service = ApiHandler.getmInstance().getService();

    long id;

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

        mMessageContainer = findViewById(R.id.rv_messages);

        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseDiscussions.this, MainActivity.class));
            }
        });

        LinearLayout lv = findViewById(R.id.lv_message);
        lv.bringToFront();
        getMessages();
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
                        }
                        else {
                            String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(ChooseDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<DiscussionsResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(ChooseDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                });
            }
        });
    }

    private void getMessages(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.getMessageByDiscussion(selectedDiscussionId).enqueue(new Callback<GetMessageByDiscussionResponseBody>() {
                    @Override
                    public void onResponse(Call<GetMessageByDiscussionResponseBody> call, Response<GetMessageByDiscussionResponseBody> response) {
                        if(response.isSuccessful()){
                            mMessages = (List<MessageResponseBody>) response.body();
                            messageAdapter = new MessageAdapter(mMessages, ChooseDiscussions.this);

                            SnapHelper snapHelper = new PagerSnapHelper();
                            LinearLayoutManager manager = new LinearLayoutManager(ChooseDiscussions.this, LinearLayoutManager.VERTICAL, false);
                            mMessageContainer.setLayoutManager(manager);
                            mMessageContainer.setAdapter(messageAdapter);
                        }
                        else {
                            String message = ErrorUtils.error(response).getError();
                            new AlertDialog.Builder(ChooseDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetMessageByDiscussionResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(ChooseDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                });
            }
        });
    }
}