package com.example.mytrips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView registration = findViewById(R.id.register_text);
        registration.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,RegisterPage.class);
            startActivity(intent);
        });
    }
}