package com.example.needlework.Discussions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.AsyncDifferConfig;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.needlework.Adapters.CategoriesAdapter;
import com.example.needlework.Adapters.MessageAdapter;
import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.discussions.DiscussionsResponseBody;
import com.example.needlework.NetWork.Models.discussions.GetDiscussionByCritetionResponseBody;
import com.example.needlework.NetWork.Models.discussions.UpdateRatingForDiscussionRequestBody;
import com.example.needlework.NetWork.Models.knittingPatterns.CategoriesOfPatternResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.UpdateRatingForPatternRequestBody;
import com.example.needlework.NetWork.Models.messages.CreateMessageRequestBody;
import com.example.needlework.NetWork.Models.messages.CreateMessageResponseBody;
import com.example.needlework.NetWork.Models.messages.GetMessageByDiscussionResponseBody;
import com.example.needlework.NetWork.Models.messages.MessageResponseBody;
import com.example.needlework.NetWork.Models.user.RegistrationRequestBody;
import com.example.needlework.NetWork.Models.userRatingForDiscussions.CreateUserRatingForDiscussionRequestBody;
import com.example.needlework.NetWork.Models.userRatingForDiscussions.GetAverageUserRatingForDiscussionResponseBody;
import com.example.needlework.NetWork.Models.userRatingForDiscussions.UserRatingsForDiscussionsResponseBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.CreateUserRatingForPatternRequestBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.GetAverageUserRatingForPatternResponseBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.UserRatingForPatternsResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.Patterns.ChoosePattern;
import com.example.needlework.R;
import com.example.needlework.SignUp;
import com.example.needlework.common.Constants;
import com.example.needlework.secondFragment;

import java.text.DecimalFormat;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseDiscussions extends AppCompatActivity {

    private long selectedDiscussionId;
    private String token;
    private long userId;

    private TextView discussionText;
    private TextView ratingText;

    private SharedPreferences sharedPreferences;

    private MessageAdapter messageAdapter;
    private List<MessageResponseBody> mMessages;
    private RecyclerView mMessageContainer;

    MaterialRatingBar ratingbar;

    private ApiService service = ApiHandler.getmInstance().getService();

    EditText etText;
    Button btn_createDisc;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_discussions);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            selectedDiscussionId = args.getLong("discussionId");
        }

        sharedPreferences = getSharedPreferences(Constants.storageName, MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        userId = sharedPreferences.getLong("userId", 0);
        Log.d("Token", token);


        discussionText = findViewById(R.id.discussion_text);
        getSelectedDiscussion();

        mMessageContainer = findViewById(R.id.rv_messages);
        etText = findViewById(R.id.etMessage);
        btn_createDisc = findViewById(R.id.btn_createDisc);
        ratingText = findViewById(R.id.rating_text);
        ratingbar = findViewById(R.id.rating);

        getMessages();
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                setRating(rating);
            }
        });

        btn_createDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMessage();
            }
        });


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
                            ratingText.setText(String.format("%.1f", response.body().getRating()));
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
                            mMessages = response.body().getMessages();
                            messageAdapter = new MessageAdapter(mMessages, ChooseDiscussions.this);

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

    private void setMessage(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.createMessage(createMessage()).enqueue(new Callback<CreateMessageResponseBody>() {
                    @Override
                    public void onResponse(Call<CreateMessageResponseBody> call, Response<CreateMessageResponseBody> response) {
                        if(response.isSuccessful()){
                            MessageResponseBody message = response.body().getMessage();
                            mMessages.add(message);
                            messageAdapter.notifyDataSetChanged();
                            etText.clearFocus();
                            etText.setText("");
                        }
                        else{
                            Toast.makeText(ChooseDiscussions.this, "Невозможно отправить сообщение", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateMessageResponseBody> call, Throwable t) {
                        Toast.makeText(ChooseDiscussions.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public CreateMessageRequestBody createMessage(){
        return new CreateMessageRequestBody(token, etText.getText().toString(), selectedDiscussionId, userId);
    }

    private void setRating(float rating) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // устанавливаю рейтинг схемы для текущего пользователя
                service.createRatingForDiscussion(new CreateUserRatingForDiscussionRequestBody(token, rating, userId, selectedDiscussionId)).enqueue(new Callback<UserRatingsForDiscussionsResponseBody>() {
                    @Override
                    public void onResponse(Call<UserRatingsForDiscussionsResponseBody> call, Response<UserRatingsForDiscussionsResponseBody> response) {
                        if (response.isSuccessful()) {
                            String message = "Рейтинг установлен: " + String.valueOf(response.body().getRating());
                            Toast.makeText(ChooseDiscussions.this, message, Toast.LENGTH_SHORT).show();

                            // получаю среднее арифметическое значение рейтинга для текущего пользователя
                            service.getAverageRatingForDiscussion(selectedDiscussionId).enqueue(new Callback<GetAverageUserRatingForDiscussionResponseBody>() {
                                @Override
                                public void onResponse(Call<GetAverageUserRatingForDiscussionResponseBody> call, Response<GetAverageUserRatingForDiscussionResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        ratingText.setText(String.valueOf(response.body().getAverageRating()));
                                        // устанавливаю среднее арифметическое значение рейтинга для текущей схемы
                                        service.updateDiscussionRating(new UpdateRatingForDiscussionRequestBody(selectedDiscussionId, response.body().getAverageRating())).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.isSuccessful()) {
                                                    new AlertDialog.Builder(ChooseDiscussions.this).setTitle("Успешно").setMessage("Рейтинг схемы обновлён").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    }).show();
                                                } else {
                                                    String message = ErrorUtils.error(response).getError();
                                                    new AlertDialog.Builder(ChooseDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    }).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                new AlertDialog.Builder(ChooseDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                }).show();
                                            }
                                        });
                                    } else {
                                        String message = ErrorUtils.error(response).getError();
                                        new AlertDialog.Builder(ChooseDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<GetAverageUserRatingForDiscussionResponseBody> call, Throwable t) {
                                    new AlertDialog.Builder(ChooseDiscussions.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                                }
                            });
                        } else {
                            String message = ErrorUtils.error(response).getError();
                            new AlertDialog.Builder(ChooseDiscussions.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRatingsForDiscussionsResponseBody> call, Throwable t) {
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