package com.example.appmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ArrayList<Double> list = new ArrayList<Double>();

        TextView txtResult4 = (TextView) findViewById(R.id.textView4);

        Intent intent = getIntent();
        double num1 = intent.getDoubleExtra("numStr1", 0);
        double num2 = intent.getDoubleExtra("numStr2", 0);

        list.add(num1);
        list.add(num2);

        double media = list.stream().reduce(0.0, Double::sum) / list.size();

        txtResult4.setText(String.format("%f", media));
    }

    public void onReturnClick(View v){
        Intent initialScreen = new Intent(this, MainActivity.class);
        startActivity(initialScreen);
    }
}