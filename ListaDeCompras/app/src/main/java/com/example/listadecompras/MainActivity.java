package com.example.listadecompras;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.listadecompras.adapter.ShoppingListHelper;

public class MainActivity extends AppCompatActivity {

    private ShoppingListHelper shoppingListHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingListHelper = new ShoppingListHelper(this);
    }
}