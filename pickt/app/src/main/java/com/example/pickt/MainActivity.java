package com.example.pickt;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Account account;
    Chat chat;
    Home home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        account = new Account();
        chat = new Chat();
        home = new Home();

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, home).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, home).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.chat:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, chat).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.account:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, account).commitAllowingStateLoss();
                        return true;
                    }
                    default:return false;
                }
            }
        });
    }
}