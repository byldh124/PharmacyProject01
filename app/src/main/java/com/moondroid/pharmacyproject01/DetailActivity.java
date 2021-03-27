package com.moondroid.pharmacyproject01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv = findViewById(R.id.tv);
        Gson gson = new Gson();
        ItemVO itemVO = gson.fromJson(getIntent().getStringExtra("item"), ItemVO.class);
        tv.setText(itemVO.getName());
    }
}