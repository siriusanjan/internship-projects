package com.example.homay.cropandrotate;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class ZoomActivity extends AppCompatActivity {
    Button buttonRotate, buttonZoom;
    SeekBar seekBarZoom;
    ImageView imageViewContainer;
    Bitmap bitmapForest;
    Matrix zoom, setToImageView;
    float bitmapHeight, bitmapWidth, screenHeight, screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        buttonRotate = findViewById(R.id.button_rotate);
        seekBarZoom = findViewById(R.id.seekbar_zoom);
        imageViewContainer = findViewById(R.id.imageview_zoom_container);
        buttonZoom = findViewById(R.id.button_zoom);

        //next button
        buttonRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ZoomActivity.this, MainActivity.class));
            }
        });

        //screen height and width
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        Log.i("Inside zoomact", "height" + screenHeight);
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;


        //create a new bitmap
        bitmapForest = BitmapFactory.decodeResource(getResources(), R.drawable.forest);
        bitmapHeight = (float) bitmapForest.getHeight();
        bitmapWidth = (float) bitmapForest.getWidth();


        buttonZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setToImageView = new Matrix();

                setToImageView.postScale(2, 2);

                Bitmap bm = Bitmap.createBitmap(bitmapForest, 0, 0, bitmapForest.getWidth(), bitmapForest.getHeight(), setToImageView, true);
                imageViewContainer.setImageBitmap(bm);
            }
        });


        //zoom with seekbar stuff

        seekBarZoom.setMax(2);


        seekBarZoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setToImageView = new Matrix();

                setToImageView.postScale(seekBar.getProgress(), seekBar.getProgress());

                setToImageView.postScale(seekBar.getProgress(), seekBar.getProgress());

                Bitmap bm = Bitmap.createBitmap(bitmapForest, 0, 0, bitmapForest.getWidth(), bitmapForest.getHeight(), setToImageView, true);
                imageViewContainer.setImageBitmap(bm);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                setToImageView = new Matrix();

                setToImageView.postScale(seekBar.getProgress(), seekBar.getProgress());


                setToImageView.postScale(seekBar.getProgress(), seekBar.getProgress());
                Bitmap bm = Bitmap.createBitmap(bitmapForest, 0, 0, bitmapForest.getWidth(), bitmapForest.getHeight(), setToImageView, true);
                imageViewContainer.setImageBitmap(bm);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setToImageView = new Matrix();

                setToImageView.postScale(seekBar.getProgress(), seekBar.getProgress());


                setToImageView.postScale(seekBar.getProgress(), seekBar.getProgress());
                Bitmap bm = Bitmap.createBitmap(bitmapForest, 0, 0, bitmapForest.getWidth(), bitmapForest.getHeight(), setToImageView, true);
                imageViewContainer.setImageBitmap(bm);
            }
        });


    }
}
