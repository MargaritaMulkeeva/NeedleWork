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
import android.widget.Toast;

import com.example.needlework.Adapters.KnittingPatternMarkdownParser;
import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternResponseBody;
import com.example.needlework.NetWork.Models.knittingPatterns.UpdateRatingForPatternRequestBody;
import com.example.needlework.NetWork.Models.userBookmarks.CreateUserBookmarkRequestBody;
import com.example.needlework.NetWork.Models.userBookmarks.UserBookmarksResponseBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.CreateUserRatingForPatternRequestBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.GetAverageUserRatingForPatternResponseBody;
import com.example.needlework.NetWork.Models.userRatingForPatterns.UserRatingForPatternsResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;
import com.example.needlework.common.Constants;
import com.squareup.picasso.Picasso;

import org.commonmark.node.Node;

import io.noties.markwon.Markwon;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChoosePattern extends AppCompatActivity {

    private long selectedPattern;

    private TextView nameText;
    private TextView ratingText;
    private ImageView imageOfProduct;
    private TextView description;
    private ImageView imageOfPattern;
    private TextView instruction;

    private ImageButton btn_addBookmark;
    MaterialRatingBar ratingbar;

    SharedPreferences sharedPreferences;
    String token;
    long userId;

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
        ratingbar = findViewById(R.id.rating);

        sharedPreferences = getSharedPreferences(Constants.storageName, MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        userId = sharedPreferences.getLong("userId", 0);

        getSelectedPattern();

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (token.equals("")) {
                    new AlertDialog.Builder(ChoosePattern.this).setTitle("Ошибка").setMessage("Войдите в аккаунт для оценивания схем").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                } else {
                    setRating(rating);
                }
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
                if (token.equals("")) {
                    new AlertDialog.Builder(ChoosePattern.this).setTitle("Ошибка").setMessage("Войдите в аккаунт для добавления схем в закладки").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                } else {
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
                            ratingText.setText(String.format("%.1f", response.body().getRating()));
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
                        if (response.isSuccessful()) {
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

    private void setRating(float rating) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // устанавливаю рейтинг схемы для текущего пользователя
                service.createRatingForPattern(new CreateUserRatingForPatternRequestBody(token, rating, userId, selectedPattern)).enqueue(new Callback<UserRatingForPatternsResponseBody>() {
                    @Override
                    public void onResponse(Call<UserRatingForPatternsResponseBody> call, Response<UserRatingForPatternsResponseBody> response) {
                        if (response.isSuccessful()) {
                            String message = "Рейтинг установлен: " + String.valueOf(response.body().getRating());
                            Toast.makeText(ChoosePattern.this, message, Toast.LENGTH_SHORT).show();

                            // получаю среднее арифметическое значение рейтинга для текущего пользователя
                            service.getAverageRatingForPattern(selectedPattern).enqueue(new Callback<GetAverageUserRatingForPatternResponseBody>() {
                                @Override
                                public void onResponse(Call<GetAverageUserRatingForPatternResponseBody> call, Response<GetAverageUserRatingForPatternResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        ratingText.setText(String.valueOf(response.body().getAverageRating()));
                                        // устанавливаю среднее арифметическое значение рейтинга для текущей схемы
                                        service.updatePatternRating(new UpdateRatingForPatternRequestBody(selectedPattern, response.body().getAverageRating())).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.isSuccessful()) {
                                                    new AlertDialog.Builder(ChoosePattern.this).setTitle("Успешно").setMessage("Рейтинг схемы обновлён").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                        }
                                                    }).show();
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
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                new AlertDialog.Builder(ChoosePattern.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                }).show();
                                            }
                                        });
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
                                public void onFailure(Call<GetAverageUserRatingForPatternResponseBody> call, Throwable t) {
                                    new AlertDialog.Builder(ChoosePattern.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                                }
                            });
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
                    public void onFailure(Call<UserRatingForPatternsResponseBody> call, Throwable t) {
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