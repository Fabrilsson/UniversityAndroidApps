package com.example.trinityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    private Intent _registrationSuccessIntent;
    private Intent _registrationErrorIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        _registrationSuccessIntent = new Intent(this, RegistrationSuccessActivity.class);


        _registrationErrorIntent = new Intent(this, RegistrationErrorActivity.class);
    }

    public void onSaveClick(View v) {

        String name = ((EditText)findViewById(R.id.editTextTextPersonName)).getText().toString();
        String address = ((EditText)findViewById(R.id.editTextTextPersonName2)).getText().toString();
        String number = ((EditText)findViewById(R.id.editTextNumber)).getText().toString();
        String postalCode = ((EditText)findViewById(R.id.editTextTextPersonName3)).getText().toString();
        String complement = ((EditText)findViewById(R.id.editTextTextPersonName4)).getText().toString();
        String email = ((EditText)findViewById(R.id.editTextTextEmailAddress2)).getText().toString();

        if( name.isEmpty() ||
            address.isEmpty() ||
            number.isEmpty() ||
            postalCode.isEmpty() ||
            complement.isEmpty() ||
            email.isEmpty()) {

            _registrationErrorIntent.putExtra("name", name);
            _registrationErrorIntent.putExtra("address", address);
            _registrationErrorIntent.putExtra("number", number);
            _registrationErrorIntent.putExtra("postalCode", postalCode);
            _registrationErrorIntent.putExtra("complement", complement);
            _registrationErrorIntent.putExtra("email", email);

            startActivity(_registrationErrorIntent);
            return;
        }

        _registrationSuccessIntent.putExtra("name", name);
        startActivity(_registrationSuccessIntent);
    }
}