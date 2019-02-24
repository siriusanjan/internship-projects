package com.example.homay.ysurveryapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    TextView appDis, deviceDis, nameDis, locationDis,phoneDis,imageDis;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        appDis = findViewById(R.id.app_name_display);
        deviceDis = findViewById(R.id.device_display);
        nameDis = findViewById(R.id.name_display);
        locationDis = findViewById(R.id.location_display);
        phoneDis = findViewById(R.id.phone_display);
        imageDis = findViewById(R.id.image_display);




        int position = getIntent().getIntExtra("position", 0);

        DataModel dataModel = GlobalData.getGlobaldata().get(position);

        //set data
        appDis.setText(dataModel.getApp());
        deviceDis.setText(dataModel.getDevice());
        nameDis.setText(dataModel.getName());
        locationDis.setText(dataModel.getLocation());
        phoneDis.setText(dataModel.getPhone());
        imageDis.setText(dataModel.getFiles().size() + "Image(s)");


        ArrayList<String> images = dataModel.getFiles();
        for (int i = 0; i < images.size(); i++) {

            Log.i("Images" , images.get(i));
        }


        recyclerView = findViewById(R.id.image_rv_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ImageAdapter(images ,this));







    }

}
