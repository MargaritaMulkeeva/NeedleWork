package com.example.needlework.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.needlework.Adapters.DiscussionAdapter;
import com.example.needlework.Adapters.OnAdapterItemClickListener;
import com.example.needlework.Adapters.UserDiscussionsAdapter;
import com.example.needlework.Discussions.ChooseDiscussions;
import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;
import com.example.needlework.common.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDiscussions extends AppCompatActivity {
    ImageButton btn_back;
    SharedPreferences sharedPreferences;
    TextView text;

    private List<DiscussionsResponseBody> discussions;
    private RecyclerView discussionsListView;
    private UserDiscussionsAdapter adapter;

    private ApiService service = ApiHandler.getmInstance().getService();
    private long userId;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_discussions);

        sharedPreferences = getSharedPreferences(Constants.storageName, MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", 0);
        token = sharedPreferences.getString("token", "");
        text = findViewById(R.id.textf);

        discussionsListView = findViewById(R.id.user_discussions_list);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDiscussions.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getUserDiscussions();
    }

    private void getUserDiscussions() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.getDiscussionsByUserId(userId).enqueue(new Callback<List<DiscussionsResponseBody>>() {
                    @Override
                    public void onResponse(Call<List<DiscussionsResponseBody>> call, Response<List<DiscussionsResponseBody>> response) {
                        if (response.isSuccessful()) {
                            discussions = response.body();
                            adapter = new UserDiscussionsAdapter(discussions, UserDiscussions.this, new OnAdapterItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Intent intent = new Intent(UserDiscussions.this, ChooseDiscussions.class);
                                    intent.putExtra("discussionId", discussions.get(position).getId());
                                    startActivity(intent);
                                }
                            });
                            LinearLayoutManager manager = new LinearLayoutManager(UserDiscussions.this, LinearLayoutManager.VERTICAL, false);
                            discussionsListView.setLayoutManager(manager);
                            discussionsListView.setAdapter(adapter);
                            if(adapter.getItemCount()!=0){
                                discussionsListView.setVisibility(View.VISIBLE);
                                text.setVisibility(View.GONE);
                            }
                            else {
                                discussionsListView.setVisibility(View.GONE);
                                text.setVisibility(View.VISIBLE);
                            }
                        } else {
                            String message = ErrorUtils.error(response).getError();
                            new AlertDialog.Builder(UserDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DiscussionsResponseBody>> call, Throwable t) {
                        new AlertDialog.Builder(UserDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
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