package com.example.homay.kanvastouch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
MyCustomView myCustomView;
KustomView cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myCustomView = new MyCustomView(this, null);
setContentView(myCustomView);

}}
