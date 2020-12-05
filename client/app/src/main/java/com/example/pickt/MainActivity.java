package com.example.pickt;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    // fragment class.java를 가져오는군
    AccountFragment account;
    ChatFragment chat;
    HomeFragment home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        */
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        account = new AccountFragment();
        chat = new ChatFragment();
        home = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, home).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.goHome:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, home).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.goChat:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, chat).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.goAccount:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, account).commitAllowingStateLoss();
                        return true;
                    }
                    default:return false;
                }
            }
        });
    }
}