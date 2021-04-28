package com.example.trinityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Intent _successLoginIntent;
    private Intent _errorLoginIntent;
    private EditText _email;
    private EditText _pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _email = (EditText)findViewById((R.id.editTextTextEmailAddress));
        _pass = (EditText)findViewById((R.id.editTextTextPassword));

        _successLoginIntent = new Intent(this, LoginSuccessActivity.class);
        _errorLoginIntent = new Intent(this, LoginErrorActivity.class);
    }

    public void onLoginClick(View v) {
        String email = _email.getText().toString();
        String pass = _pass.getText().toString();

        if (email.contains("@acad.ftec.com.br") &&
            pass.equals("123456")){
            startActivity(_successLoginIntent);
            return;
        }

        startActivity(_errorLoginIntent);
    }
}