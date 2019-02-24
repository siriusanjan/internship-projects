package com.example.homay.customview;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import ch.halcyon.squareprogressbar.SquareProgressBar;

public class MainActivity extends AppCompatActivity {
    SquareProgressBar squareProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        squareProgressBar = findViewById(R.id.my_progressbar);
        squareProgressBar.setProgress(50.0);

    }
}
