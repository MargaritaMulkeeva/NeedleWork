package com.example.needlework.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.example.needlework.MainActivity;
import com.example.needlework.NetWork.ApiHandler;
import com.example.needlework.NetWork.Service.ApiService;
import com.example.needlework.R;

public class UserBookMark extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView rvBookMark;

    SharedPreferences sharedPreferences;

    ApiService service = ApiHandler.getmInstance().getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_mark);

        imgBack = findViewById(R.id.btn_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserBookMark.this, MainActivity.class);
                startActivity(intent);
            }
        });
        rvBookMark = findViewById(R.id.rvBookMark);
    }

    private void getBookMark(){
    }
}