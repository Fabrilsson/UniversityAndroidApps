package com.example.trinityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginErrorActivity extends AppCompatActivity {

    private Intent _loginIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_error);

        _loginIntent = new Intent(this, LoginActivity.class);
    }

    public void onReturnClick(View v){
        startActivity(_loginIntent);
    }
}