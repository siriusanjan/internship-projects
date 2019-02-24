package com.example.homay.kanvastouch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DrawActivity extends AppCompatActivity {
    KustomView kustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kustomView = new KustomView(this, null);

    setContentView(kustomView);
    }
}
