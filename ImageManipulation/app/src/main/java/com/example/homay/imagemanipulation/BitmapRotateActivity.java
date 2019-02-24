package com.example.homay.imagemanipulation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapRotateActivity extends AppCompatActivity {
    Bitmap globalBitmap;
    Bitmap globalBitmapRotated, globalBitmapRotatedtest;
    Matrix globalMatrix;

    ImageView myImageView;
    Button btnSave;
    SeekBar seekBar;
    EditText rotationDegree;
    Button btnRotate, btnAnimage, btnReset;
    Float degreeValue;

    Float defaultRotation = 90F;
    Float secondDefault = 0F;
    //flag values

    Boolean firstTime = true;
    Boolean reset = false;
    Boolean input_isEmpty = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        seekBar = findViewById(R.id.seekbar);

        btnSave = findViewById(R.id.button_save);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Integer xMax = myImageView.getDrawable().getBounds().width();
                Integer yMax = myImageView.getDrawable().getBounds().width();

                File path = Environment.getExternalStorageDirectory();
                File file = new File(path, "Img.jpg");
                globalMatrix.postRotate(20);
                Integer y = globalBitmap.getHeight();
                Integer x = globalBitmap.getWidth();
                globalBitmapRotatedtest = Bitmap.createBitmap(globalBitmap, 0, 0, globalBitmap.getWidth(), globalBitmap.getHeight(), globalMatrix, true);
                globalBitmapRotated = Bitmap.createBitmap(globalBitmapRotatedtest, 0, 0, y - (globalBitmapRotatedtest.getWidth() - y), globalBitmap.getHeight());
                try (FileOutputStream out = new FileOutputStream(file)) {
                    globalBitmapRotated.compress(Bitmap.CompressFormat.JPEG, 90, out);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        seekBar.setMax(360);
        //Bitmap and matrix stuff
        decodeBitmap();
        globalMatrix = new Matrix();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                globalMatrix.postRotate(progress);

                globalBitmapRotated = Bitmap.createBitmap(globalBitmap, 0, 0, globalBitmap.getWidth(), globalBitmap.getHeight(), globalMatrix, true);
                myImageView.setImageBitmap(globalBitmapRotated);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                globalMatrix.postRotate(seekBar.getProgress());
                globalBitmapRotated = Bitmap.createBitmap(globalBitmap, 0, 0, globalBitmap.getWidth(), globalBitmap.getHeight(), globalMatrix, true);
                myImageView.setImageBitmap(globalBitmapRotated);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                globalMatrix.postRotate(seekBar.getProgress());
                globalBitmapRotated = Bitmap.createBitmap(globalBitmap, 0, 0, globalBitmap.getWidth(), globalBitmap.getHeight(), globalMatrix, true);
                myImageView.setImageBitmap(globalBitmapRotated);

            }
        });

        myImageView = findViewById(R.id.image_container);
        rotationDegree = findViewById(R.id.edittext_rotation_degree);
        btnRotate = findViewById(R.id.rotate_button);
        btnAnimage = findViewById(R.id.animate_button);

        btnReset = findViewById(R.id.button_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });


        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Matrix matrix = new Matrix();
//check if input is empty

                if (rotationDegree.getText().toString().isEmpty()) {
                    input_isEmpty = true;
                } else {
                    input_isEmpty = false;
                }
                //check post rotate condition

                if (firstTime && input_isEmpty) {
                    globalMatrix.postRotate(defaultRotation);

                    firstTime = false;

                    Log.i("Position:", "Inside first time with no data");
                }


                //first time with data
                else if (firstTime && !input_isEmpty) {
                    Float degree = Float.parseFloat(rotationDegree.getText().toString());
                    globalMatrix.postRotate(degree);
                    secondDefault = degree;


                    Log.i("Position:", "Inside first time with data");
                    firstTime = false;
                }

                //non-first time without data
                else if (!firstTime && input_isEmpty) {

                    defaultRotation += 90;
                    globalMatrix.postRotate(defaultRotation);
                    Log.i("Position:", "Inside non-first time with no data");
                }

                //non-first time with data
                else if (!firstTime && !input_isEmpty) {
                    Float anotherDegree = Float.parseFloat(rotationDegree.getText().toString());
                    secondDefault += anotherDegree;
                    globalMatrix.postRotate(secondDefault);
                    Log.i("Position:", "Inside non-first time with data");
                } else {
                    //do something
                    Log.i("Positon", "No condition matced");
                }


                globalBitmapRotated = Bitmap.createBitmap(globalBitmap, 0, 0, globalBitmap.getWidth(), globalBitmap.getHeight(), globalMatrix, true);
                myImageView.setImageBitmap(globalBitmapRotated);


            }
        });


    }

    public void resetData() {
        globalMatrix.postRotate(0);
        globalBitmapRotated = Bitmap.createBitmap(globalBitmap, 0, 0, globalBitmap.getWidth(), globalBitmap.getHeight(), globalMatrix, true);
        myImageView.setImageBitmap(globalBitmapRotated);

        Log.i("Stat ", "Reset Done");

        rotationDegree.setText(null);
        //reset all flags


        defaultRotation = 90F;
        secondDefault = 0F;
        //flag values

        firstTime = true;
        reset = false;
        input_isEmpty = true;

    }


    public void decodeBitmap() {
        globalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.animal);
    }
}

