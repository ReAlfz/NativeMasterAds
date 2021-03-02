package com.example.nativeadsmaster_realfz_;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String use = getIntent().getStringExtra("title");
        TextView textView = findViewById(R.id.textView);
        textView.setText(use);
    }
}