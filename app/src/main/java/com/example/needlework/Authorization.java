package com.example.needlework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.needlework.common.Constants;

public class Authorization extends AppCompatActivity {

    Button signUp, signIn, btn_signInWithoutAccount;

    private SharedPreferences sharedPreferences;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        sharedPreferences = getSharedPreferences(Constants.storageName, MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        if(!token.equals("")){
            Intent intent = new Intent(Authorization.this, MainActivity.class);
            startActivity(intent);
        }

        signIn = findViewById(R.id.btn_signIn);
        signUp = findViewById(R.id.btn_signUp);
        btn_signInWithoutAccount = findViewById(R.id.btn_signInWithoutAccount);

        btn_signInWithoutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(token.equals("")){
                    Intent intent = new Intent(Authorization.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Authorization.this, SignIn.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Authorization.this, SignUp.class);
                startActivity(intent);
            }
        });


    }
}