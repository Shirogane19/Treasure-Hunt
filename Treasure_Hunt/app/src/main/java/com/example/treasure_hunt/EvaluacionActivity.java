package com.example.treasure_hunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;

public class EvaluacionActivity extends AppCompatActivity {

    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion);

        ratingBar = findViewById(R.id.ratingBar);

    }
}
