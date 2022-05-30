package com.example.needlework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.needlework.NetWork.ErrorUtils;
import com.example.needlework.common.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {

    firstFragment patterns = new firstFragment();
    secondFragment discussions = new secondFragment();
    thirdFragment profile = new thirdFragment();

    private SharedPreferences sharedPreferences;
    String token;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navig);

        sharedPreferences = getSharedPreferences(Constants.storageName, MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, patterns).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.firstFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, patterns).commit();
                return true;
            case R.id.secondFragment:
                if(!token.equals("")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, discussions).commit();
                }
                else {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Ошибка").setMessage("Войдите в аккаунт для того, чтобы просматривать обсуждения").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
                return true;
            case R.id.thirdFragment:
                if(!token.equals("")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, profile).commit();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, ProfileForUnregisteredUser.class);
                    startActivity(intent);
                }
                return true;
        }
        return false;
    }
}