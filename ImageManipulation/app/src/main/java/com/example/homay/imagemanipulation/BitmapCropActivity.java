package com.example.homay.imagemanipulation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class BitmapCropActivity extends AppCompatActivity {
    Button btnCrop, btnRotate;
    EditText editTextRoatation;
    ImageView imageViewContainer,imageViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnCrop = findViewById(R.id.button_crop);
        btnRotate = findViewById(R.id.btn_rotate);
        editTextRoatation = findViewById(R.id.user_input);
        imageViewContainer = findViewById(R.id.crop_container);
        imageViewResult = findViewById(R.id.final_image_container);

        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.deer);
        try {
            imageViewContainer.setImageBitmap(bm);
        } catch (NullPointerException E) {
            E.printStackTrace();
        }


    }
}
