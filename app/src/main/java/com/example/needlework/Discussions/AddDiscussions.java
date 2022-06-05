package com.example.needlework.Discussions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.needlework.Adapters.DiscussionAdapter;
import com.example.needlework.Adapters.OnAdapterItemClickListener;
import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.discussions.CategoryOfDiscussionResponseBody;
import com.example.needlework.NetWork.Models.discussions.CreateCategoryOfDiscussionRequestBody;
import com.example.needlework.NetWork.Models.discussions.CreateDiscussionRequestBody;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;
import com.example.needlework.common.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDiscussions extends AppCompatActivity {

    ImageButton img_back;

    private DiscussionAdapter popularDiscussionAdapter;
    private List<DiscussionsResponseBody> mPopularDiscussions;
    private RecyclerView recyclerViewPopularDisc;

    RecyclerView rvDisc;
    String token;
    float rating = 10;
    private long userId;
    private SharedPreferences sharedPreferences;

    EditText et_category, et_message, etTheme;
    Button publishButton;

    private ApiService service = ApiHandler.getmInstance().getService();

    private long categoryOfDiscussionId = 0;
    private boolean isCategoryOfDiscussionCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussions);

        categoryOfDiscussionId = 0;
        isCategoryOfDiscussionCreated = false;

        img_back = findViewById(R.id.btn_back);
        recyclerViewPopularDisc = findViewById(R.id.rv_popular);
        et_category = findViewById(R.id.et_category);
        et_message = findViewById(R.id.et_message);
        etTheme = findViewById(R.id.et_theme);
        publishButton = findViewById(R.id.btn_publish);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishDiscussion();
            }
        });

        token = getSharedPreferences(Constants.storageName, Context.MODE_PRIVATE).getString("token", "");
        sharedPreferences = getSharedPreferences(Constants.storageName, MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", 0);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDiscussions.this, ChooseDiscussions.class);
                startActivity(intent);
            }
        });

        getPopularCategories();
    }

    private void getPopularCategories() {
        AsyncTask.execute(() -> {
            service.getDiscussionsByCriterion("popular").enqueue(new Callback<GetDiscussionByCritetionResponseBody>() {
                @Override
                public void onResponse(Call<GetDiscussionByCritetionResponseBody> call, Response<GetDiscussionByCritetionResponseBody> response) {
                    if (response.isSuccessful()) {
                        mPopularDiscussions = response.body().getDiscussions();
                        popularDiscussionAdapter = new DiscussionAdapter(mPopularDiscussions, AddDiscussions.this, new OnAdapterItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(AddDiscussions.this, ChooseDiscussions.class);
                                intent.putExtra("discussionId", mPopularDiscussions.get(position).getId());
                                startActivity(intent);
                            }
                        });

                        LinearLayoutManager manager = new LinearLayoutManager(AddDiscussions.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewPopularDisc.setLayoutManager(manager);
                        recyclerViewPopularDisc.setAdapter(popularDiscussionAdapter);
                    } else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(AddDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<GetDiscussionByCritetionResponseBody> call, Throwable t) {
                    new AlertDialog.Builder(AddDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }

    private void publishDiscussion() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.createCategoryOfDiscussion(new CreateCategoryOfDiscussionRequestBody(et_category.getText().toString())).enqueue(new Callback<CategoryOfDiscussionResponseBody>() {
                    @Override
                    public void onResponse(Call<CategoryOfDiscussionResponseBody> call, Response<CategoryOfDiscussionResponseBody> response) {
                        if (response.isSuccessful()) {
                            categoryOfDiscussionId = response.body().getId();
                        } else {
                            String message = ErrorUtils.error(response).getError();
                            new AlertDialog.Builder(AddDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryOfDiscussionResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(AddDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                });

                service.createDiscussion(new CreateDiscussionRequestBody(etTheme.getText().toString(), et_message.getText().toString(), 0, categoryOfDiscussionId, userId)).enqueue(new Callback<DiscussionsResponseBody>() {
                    @Override
                    public void onResponse(Call<DiscussionsResponseBody> call, Response<DiscussionsResponseBody> response) {
                        new AlertDialog.Builder(AddDiscussions.this).setTitle("Успешно").setMessage("Обсуждение успешно создано").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(AddDiscussions.this, MainActivity.class));
                            }
                        }).show();
                    }

                    @Override
                    public void onFailure(Call<DiscussionsResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(AddDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
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