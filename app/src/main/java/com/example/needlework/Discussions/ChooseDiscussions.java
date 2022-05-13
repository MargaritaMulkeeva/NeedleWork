package com.example.needlework.Discussions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.needlework.R;

public class ChooseDiscussions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_discussions);

        LinearLayout lv = findViewById(R.id.lv_message);

        lv.bringToFront();
    }
}