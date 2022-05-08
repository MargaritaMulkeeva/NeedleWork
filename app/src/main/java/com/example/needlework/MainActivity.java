package com.example.needlework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    firstFragment main = new firstFragment();
    secondFragment discussion = new secondFragment();
    thirdFragment profile = new thirdFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.firstFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.firstFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, main).commit();
                return true;
            case R.id.secondFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, discussion).commit();
                return true;
            case R.id.thirdFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, profile).commit();
                return true;
        }
        return false;
    }
}