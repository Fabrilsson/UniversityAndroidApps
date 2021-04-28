package com.example.trinityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    private Intent _siteIntent;

    private Intent _homeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String url = "https://ftec.com.br";

        _siteIntent = new Intent(Intent.ACTION_VIEW);
        _siteIntent.setData(Uri.parse(url));

        _homeIntent = new Intent(this, MainActivity.class);
    }

    public void onSiteClick(View v) {
        startActivity(_siteIntent);
    }

    public void onHomeClick(View v) {
        startActivity(_homeIntent);
    }
}