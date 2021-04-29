package com.example.trinityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegistrationErrorActivity extends AppCompatActivity {

    private Intent _homeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_error);

        String errors = "";

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String number = intent.getStringExtra("number");
        String postalCode = intent.getStringExtra("postalCode");
        String complement = intent.getStringExtra("complement");
        String email = intent.getStringExtra("email");

        if (name.isEmpty())
            errors += "Nome";
        if (address.isEmpty())
            errors += "Endereço";
        if (number.isEmpty())
            errors += "Número";
        if (postalCode.isEmpty())
            errors += "CEP";
        if (complement.isEmpty())
            errors += "Complemento";
        if (email.isEmpty())
            errors += "E-mail";

        TextView textView = (TextView) findViewById(R.id.textView5);

        String text = textView.getText().toString();
        text = text.replaceAll("%errors%", errors);

        textView.setText(text);

        _homeIntent = new Intent(this, MainActivity.class);
    }

    public void onReturnClick(View v) {
        startActivity(_homeIntent);
    }
}