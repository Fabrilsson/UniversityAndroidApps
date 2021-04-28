package com.example.trinityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent _loginIntent;
    private Intent _registrationIntent;
    private Intent _aboutIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _loginIntent = new Intent(this, LoginActivity.class);
        _registrationIntent = new Intent(this, RegistrationActivity.class);
        _aboutIntent = new Intent(this, AboutActivity.class);
    }

    public void onLoginClick(View v) {
        startActivity(_loginIntent);
    }

    public void onRegistrationClick(View v) {
        startActivity(_registrationIntent);
    }

    public void onAboutClick(View v) {
        startActivity(_aboutIntent);
    }
}