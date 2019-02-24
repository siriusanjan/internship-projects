package com.example.homay.cropandrotate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    Button buttonCrop, buttonNext;
    ImageView imageViewContainer, imageViewResult;
    Bitmap bitmapNew, bitmapRotated, bitmapCropped;
    Matrix matrixRotate, matrixFinal;
    Float rotationDegree;
    SeekBar seekBarRotate;

float imageHeight, imageWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNext = findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ZoomActivity.class));
            }
        });
        buttonCrop = findViewById(R.id.button_crop);

        imageViewContainer = findViewById(R.id.imageview_input);
        imageViewResult = findViewById(R.id.imageview_result);

//creating bitmap from the drawable image
        bitmapNew = BitmapFactory.decodeResource(getResources(), R.drawable.jungle);


        //getting width and height of the image
        imageHeight = bitmapNew.getHeight();
        imageWidth = bitmapNew.getWidth();






        seekBarRotate = findViewById(R.id.seekbar_rotate);
        seekBarRotate.setMax(50);
        seekBarRotate.setProgress(25);


        seekBarRotate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(seekBar.getProgress()>25) {
                    matrixRotate = new Matrix();
                    rotationDegree = (float) (seekBar.getProgress()) - 30 ;
                    float scale  =(float) ((progress/100.0f)+1);


                }
                else{
                    matrixRotate = new Matrix();
                    rotationDegree = (float) (seekBar.getProgress())-30;
                }

                matrixRotate.postRotate(rotationDegree);
          //      matrixRotate.postScale(2, 2);
                bitmapRotated = Bitmap.createBitmap(bitmapNew, 0, 0, bitmapNew.getWidth(), bitmapNew.getHeight(), matrixRotate, true);
                imageViewContainer.setImageBitmap(bitmapRotated);
                float scale  =(float) ((progress/90.0f)+1);
                imageViewContainer.setScaleX(scale);
                imageViewContainer.setScaleY(scale);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                if(seekBar.getProgress()>25) {
                    matrixRotate = new Matrix();
                    rotationDegree = (float) (seekBar.getProgress())-30;


                }
                else{
                    matrixRotate = new Matrix();
                    rotationDegree = (float) (seekBar.getProgress()) - 30;
                }

                matrixRotate.postRotate(rotationDegree);
          //      matrixRotate.postScale(2, 2);
                bitmapRotated = Bitmap.createBitmap(bitmapNew, 0, 0, bitmapNew.getWidth(), bitmapNew.getHeight(), matrixRotate, true);
                imageViewContainer.setImageBitmap(bitmapRotated);



            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress()>25) {
                    matrixRotate = new Matrix();
                    rotationDegree = (float) (seekBar.getProgress()) - 30 ;


                }
                else{
                    matrixRotate = new Matrix();
                    rotationDegree = (float) (seekBar.getProgress()) - 30;
                }

                matrixRotate.postRotate(rotationDegree);
//                matrixRotate.postScale(2, 2);
                bitmapRotated = Bitmap.createBitmap(bitmapNew, 0, 0, bitmapNew.getWidth(), bitmapNew.getHeight(), matrixRotate, true);
                imageViewContainer.setImageBitmap(bitmapRotated);


            }
        });




//cropping stuff

        buttonCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//             bitmapCropped = Bitmap.createBitmap(bitmapNew, (bitmapNew.getWidth() / 2) - 200, (bitmapNew.getHeight() / 2) - 200, 200, 200);
    bitmapCropped = Bitmap.createBitmap(bitmapRotated, (bitmapRotated.getWidth() / 2) - 200, (bitmapRotated.getHeight() / 2) - 200, 200, 200);

                imageViewResult.setImageBitmap(bitmapCropped);

            }
        });


    }


//end of class
}
