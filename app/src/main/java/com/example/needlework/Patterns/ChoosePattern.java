package com.example.needlework.Patterns;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.needlework.Adapters.KnittingPatternMarkdownParser;
import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternResponseBody;
import com.example.needlework.NetWork.Models.userBookmarks.CreateUserBookmarkRequestBody;
import com.example.needlework.NetWork.Models.userBookmarks.UserBookmarksResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;
import com.example.needlework.common.Constants;
import com.squareup.picasso.Picasso;

import org.commonmark.node.Node;

import io.noties.markwon.Markwon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoosePattern extends AppCompatActivity {

    private long selectedPattern;

    private TextView nameText;
    SharedPreferences sharedPreferences;
    String token;
    private TextView ratingText;
    private ImageView imageOfProduct;
    private TextView description;
    private ImageView imageOfPattern;
    private TextView instruction;

    private ImageButton btn_addBookmark;


    ApiService service = ApiHandler.getmInstance().getService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pattern);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            selectedPattern = args.getLong("patternId");
        }

        nameText = findViewById(R.id.tv_name);
        ratingText = findViewById(R.id.rating_text);
        imageOfProduct = findViewById(R.id.img_photoThing);
        description = findViewById(R.id.txt_description);
        imageOfPattern = findViewById(R.id.img_photoKnitting);
        instruction = findViewById(R.id.txt_work);
        btn_addBookmark = findViewById(R.id.btn_addBookmark);

        sharedPreferences = getSharedPreferences(Constants.storageName, MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        getSelectedPattern();

        RatingBar rb = findViewById(R.id.rating);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rb.setRating(rating);
            }
        });

        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoosePattern.this, MainActivity.class));
            }
        });

        btn_addBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(token.equals("")){
                    new AlertDialog.Builder(ChoosePattern.this).setTitle("Ошибка").setMessage("Войдите в аккаунт для добавления схем в закладки").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
                else {
                    addBookMark();
                }
            }
        });
    }

    private void getSelectedPattern() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.getKnittingPattern(selectedPattern).enqueue(new Callback<KnittingPatternResponseBody>() {
                    @Override
                    public void onResponse(Call<KnittingPatternResponseBody> call, Response<KnittingPatternResponseBody> response) {
                        if (response.isSuccessful()) {
                            nameText.setText(response.body().getName());
                            ratingText.setText(String.valueOf(response.body().getRating()));
                            description.setText(response.body().getDescription());

                            Picasso.with(ChoosePattern.this)
                                    .load(response.body().getImageOfProduct())
                                    .placeholder(R.drawable.image_placeholder)
                                    .into(imageOfProduct);

                            Picasso.with(ChoosePattern.this)
                                    .load(response.body().getImagePattern())
                                    .placeholder(R.drawable.image_placeholder)
                                    .into(imageOfPattern);

                            String markdownData = KnittingPatternMarkdownParser.GetMarkdownFromPattern(response.body().getWorkProcessDescription());
                            instruction.setText(markdownData);
                        } else {
                            String message = ErrorUtils.error(response).getError();
                            new AlertDialog.Builder(ChoosePattern.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<KnittingPatternResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(ChoosePattern.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                });
            }
        });
    }

    private void addBookMark() {
        long userId = sharedPreferences.getLong("userId", 0);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                service.createBookmark(new CreateUserBookmarkRequestBody(token, userId, selectedPattern)).enqueue(new Callback<UserBookmarksResponseBody>() {
                    @Override
                    public void onResponse(Call<UserBookmarksResponseBody> call, Response<UserBookmarksResponseBody> response) {
                        if(response.isSuccessful()) {
                                new AlertDialog.Builder(ChoosePattern.this).setTitle("Успешно").setMessage("Схема успешно добавлена в закладки").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UserBookmarksResponseBody> call, Throwable t) {
                        new AlertDialog.Builder(ChoosePattern.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
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