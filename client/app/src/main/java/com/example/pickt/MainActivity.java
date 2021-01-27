package com.example.pickt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pickt.UtilsService.SharedPreferenceClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    SharedPreferenceClass sharedPreferenceClass;
    BottomNavigationView bottomNavigationView;

    // fragment class.java를 가져오는군
    AccountFragment account;
    ChatFragment chat;
    HomeFragment home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        sharedPreferenceClass = new SharedPreferenceClass(this);

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

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences pickt_pref = getSharedPreferences("user_pickt", MODE_PRIVATE);
        if(pickt_pref.contains("token")) {
            //startActivity(new Intent(MainActivity.this, AddTrailerActivity.class));
            finish();
        }
    }


}