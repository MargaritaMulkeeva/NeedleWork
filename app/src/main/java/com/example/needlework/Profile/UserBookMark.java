package com.example.needlework.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.needlework.Adapters.AllPatternAdapter;
import com.example.needlework.Adapters.BookmarkAdapter;
import com.example.needlework.Adapters.CategoriesAdapter;
import com.example.needlework.Adapters.OnAdapterItemClickListener;
import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.NetWork.Models.knittingPatterns.GetAllKnittingPatterns;
import com.example.needlework.NetWork.Models.userBookmarks.UserBookmarksResponseBody;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.Patterns.ChoosePattern;
import com.example.needlework.R;
import com.example.needlework.common.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserBookMark extends AppCompatActivity {

    ImageView imgBack;

    SharedPreferences sharedPreferences;
    TextView text;

    ApiService service = ApiHandler.getmInstance().getService();
    String token;

    private BookmarkAdapter bookmarkAdapter;
    private List<UserBookmarksResponseBody> mBookMark;
    private RecyclerView rvBookMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_mark);
        text = findViewById(R.id.textf);

        imgBack = findViewById(R.id.btn_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserBookMark.this, MainActivity.class);
                startActivity(intent);
            }
        });
        rvBookMark = findViewById(R.id.rvBookMark);
        token = getSharedPreferences(Constants.storageName, Context.MODE_PRIVATE).getString("token", "");
        getBookMark();
    }

    private void getBookMark(){
        AsyncTask.execute(()->{
            service.getBookmarks(token).enqueue(new Callback<List<UserBookmarksResponseBody>>() {
                @Override
                public void onResponse(Call<List<UserBookmarksResponseBody>> call, Response<List<UserBookmarksResponseBody>> response) {
                    if(response.isSuccessful()){
                        mBookMark = response.body();
                        bookmarkAdapter = new BookmarkAdapter(mBookMark, UserBookMark.this, new OnAdapterItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(UserBookMark.this, ChoosePattern.class);
                                intent.putExtra("patternId", mBookMark.get(position).getKnittingPatternId());
                                startActivity(intent);
                            }
                        });

                        LinearLayoutManager manager = new LinearLayoutManager(UserBookMark.this, LinearLayoutManager.VERTICAL, false);
                        rvBookMark.setLayoutManager(manager);
                        rvBookMark.setAdapter(bookmarkAdapter);
                        if(bookmarkAdapter.getItemCount()!=0){
                            rvBookMark.setVisibility(View.VISIBLE);
                            text.setVisibility(View.GONE);
                        }
                        else {
                            rvBookMark.setVisibility(View.GONE);
                            text.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        String message = ErrorUtils.error(response).getError();
                        new AlertDialog.Builder(UserBookMark.this).setTitle("Ошибка").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }

                @Override
                public void onFailure(Call<List<UserBookmarksResponseBody>> call, Throwable t) {
                    new AlertDialog.Builder(UserBookMark.this).setTitle("Ошибка").setMessage(t.getLocalizedMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
            });
        });
    }
}