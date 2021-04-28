package com.example.appmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSendClick(View v) {
        EditText num1 = (EditText) findViewById(R.id.editTextNumber);

        EditText num2 = (EditText) findViewById(R.id.editTextNumber2);

        String numStr1 = num1.getText().toString();

        String numStr2 = num2.getText().toString();

        if(numStr1.isEmpty() || numStr2.isEmpty())
            return;

        Intent intent = new Intent(this, ResultActivity.class);

        intent.putExtra("numStr1", Double.parseDouble(numStr1));

        intent.putExtra("numStr2", Double.parseDouble(numStr2));

        startActivity(intent);
    }
}