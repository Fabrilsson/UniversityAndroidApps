package com.example.trinityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginSuccessActivity extends AppCompatActivity {

    private Intent _aboutIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        _aboutIntent = new Intent(this, AboutActivity.class);
    }

    public void onAboutClick(View v) {
        startActivity(_aboutIntent);
    }
}