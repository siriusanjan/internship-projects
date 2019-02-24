package com.example.homay.mycustomview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Paper paperMyCircle;
    Button buttonPoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paperMyCircle = findViewById(R.id.custViewPaper);

        buttonPoke = findViewById(R.id.button_poke);
        buttonPoke.setVisibility(View.INVISIBLE);
        buttonPoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paperMyCircle.setCircleCol(Color.GREEN);
                paperMyCircle.setCircleText("Ouch!");
                paperMyCircle.setLabelCol(Color.BLUE);
            }
        });
    }
}
