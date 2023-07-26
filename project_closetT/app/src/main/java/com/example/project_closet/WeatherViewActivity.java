package com.example.project_closet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class WeatherViewActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_view);

        // 뒤로가기 버튼
        ImageButton btn_back_view = findViewById(R.id.btn_back_weatherview);

        btn_back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                onBackPressed();
            }
        });
    }
}