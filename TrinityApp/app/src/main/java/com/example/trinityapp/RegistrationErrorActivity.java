package com.example.trinityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistrationErrorActivity extends AppCompatActivity {

    private Intent _homeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_error);

        _homeIntent = new Intent(this, MainActivity.class);
    }

    public void onReturnClick(View v) {
        startActivity(_homeIntent);
    }
}