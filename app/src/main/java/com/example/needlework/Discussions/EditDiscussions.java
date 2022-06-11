package com.example.needlework.Discussions;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.needlework.Adapters.DiscussionAdapter;
import com.example.needlework.Adapters.OnAdapterItemClickListener;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.discussions.CategoryOfDiscussionResponseBody;
import com.example.needlework.NetWork.Models.discussions.CreateCategoryOfDiscussionRequestBody;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionResponseBody;
import com.example.needlework.NetWork.Models.discussions.UpdateDiscussionRequestBody;
import com.example.needlework.NetWork.Models.discussions.UpdateDiscussionResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.Profile.UserDiscussions;
import com.example.needlework.R;
import com.example.needlework.common.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDiscussions extends AppCompatActivity {

    private DiscussionAdapter popularDiscussionAdapter;
    private List<DiscussionsResponseBody> mPopularDiscussions;
    private RecyclerView recyclerViewPopularDisc;

    EditText et_message, etTheme;
    Button publishButton;
    ImageButton img_back;

    ApiService service = ApiHandler.getmInstance().getService();

    static long categoryOfDiscussionId;
    long userId;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_discussions);

        sharedPreferences = getSharedPreferences(Constants.storageName, MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", 0);

        categoryOfDiscussionId = 0;

        img_back = findViewById(R.id.btn_back);
        recyclerViewPopularDisc = findViewById(R.id.rv_popular);
        et_message = findViewById(R.id.et_message);
        etTheme = findViewById(R.id.et_theme);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            long id = args.getLong("discussionId");
            getDiscussion(id);
        }

        publishButton = findViewById(R.id.btn_edit_discussion);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDiscussion();
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
                        popularDiscussionAdapter = new DiscussionAdapter(mPopularDiscussions, EditDiscussions.this, new OnAdapterItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(EditDiscussions.this, ChooseDiscussions.class);
                                intent.putExtra("discussionId", mPopularDiscussions.get(position).getId());
                                startActivity(intent);
                            }
                        });

                        LinearLayoutManager manager = new LinearLayoutManager(EditDiscussions.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewPopularDisc.setLayoutManager(manager);
                        recyclerViewPopularDisc.setAdapter(popularDiscussionAdapter);
                    } else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(EditDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<GetDiscussionByCritetionResponseBody> call, Throwable t) {
                    new AlertDialog.Builder(EditDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }

    private void getDiscussion(long id) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.getDiscussionsById(id).enqueue(new Callback<DiscussionsResponseBody>() {
                    @Override
                    public void onResponse(Call<DiscussionsResponseBody> call, Response<DiscussionsResponseBody> response) {
                        if (response.isSuccessful()) {
                            DiscussionsResponseBody discussion = response.body();
                            etTheme.setText(discussion.getTheme());
                            et_message.setText(discussion.getTextOfDiscussions());
                            categoryOfDiscussionId = discussion.getCategoryOfDiscussionsId();
                        } else {
                            String message = ErrorUtils.error(response).getError();
                            new AlertDialog.Builder(EditDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(EditDiscussions.this, UserDiscussions.class));
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DiscussionsResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(EditDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(EditDiscussions.this, UserDiscussions.class));
                            }
                        }).show();
                    }
                });
            }
        });
    }

    private void editDiscussion() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.updateDiscussion(new UpdateDiscussionRequestBody(etTheme.getText().toString(), et_message.getText().toString(), categoryOfDiscussionId, userId)).enqueue(new Callback<UpdateDiscussionResponseBody>() {
                    @Override
                    public void onResponse(Call<UpdateDiscussionResponseBody> call, Response<UpdateDiscussionResponseBody> response) {
                        if (response.isSuccessful()) {
                            new AlertDialog.Builder(EditDiscussions.this).setTitle("Успешно").setMessage(response.body().getMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(EditDiscussions.this, UserDiscussions.class));
                                }
                            }).show();
                        } else {
                            String message = ErrorUtils.error(response).getError();
                            new AlertDialog.Builder(EditDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateDiscussionResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(EditDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
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