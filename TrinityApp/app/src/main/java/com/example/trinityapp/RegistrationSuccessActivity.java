package com.example.trinityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegistrationSuccessActivity extends AppCompatActivity {

    private Intent _aboutIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_success);

        _aboutIntent = new Intent(this, AboutActivity.class);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        TextView textView = (TextView) findViewById(R.id.textView4);

        String text = textView.getText().toString();
        text.replaceAll("%name%", name);

        textView.setText(text);
    }

    public void onAboutClick(View v){
        startActivity(_aboutIntent);
    }
}