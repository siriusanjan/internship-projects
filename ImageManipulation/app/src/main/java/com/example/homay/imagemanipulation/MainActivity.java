package com.example.homay.imagemanipulation;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    Uri imageUri;
    String degree;
    Button btnNewImage, btnRotate, btnReset, btnAnimate;
    EditText degreeOfRotation;
    Integer cycle = 0;

//Flags & values

    Boolean firstTime = true;
    Boolean reset = false;
    Boolean input_isEmpty = true;
    //Values
    Float rotate_when_empty = 90F;
    Float case1 = 90F, case2 = 0F, val1, val2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReset = findViewById(R.id.button_reset);

        image = findViewById(R.id.my_image);
        btnNewImage = findViewById(R.id.select_image_button);
        btnRotate = findViewById(R.id.rotate_button);
        degreeOfRotation = findViewById(R.id.degree_rotation);
        btnAnimate = findViewById(R.id.btn_animate);


        btnAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstTime = true;

                degreeOfRotation.setText(null);
                image.setRotation(0);
            }

        });


        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check whether data is present
                if (degreeOfRotation.getText().toString().isEmpty()) {
                    input_isEmpty = true;
                } else {
                    input_isEmpty = false;
                }


//first time without data
                if (firstTime && input_isEmpty) {
                    image.setRotation(case1);
                    case1 += case1;
                    firstTime = false;

                    Log.i("Position:", "Inside first time with no data");
                }


                //first time with data
                else if (firstTime && !input_isEmpty) {
                    val1 = Float.parseFloat(degreeOfRotation.getText().toString());
                    image.setRotation(image.getRotation() + val1);
                    Log.i("Position:", "Inside first time with data");
                    firstTime = false;
                }

                //non-first time without data
                else if (!firstTime && input_isEmpty) {
                    image.setRotation(image.getRotation() + 90F);
                    Log.i("Position:", "Inside non-first time with no data");
                }

                //non-first time with data
                else if (!firstTime && !input_isEmpty) {
                    val2 = Float.parseFloat(degreeOfRotation.getText().toString());

                    image.setRotation(image.getRotation() + val2);
                    Log.i("Position:", "Inside non-first time with data");
                } else {
                    //do something
                    Log.i("Positon", "No condition matced");
                }


            }
        });
        btnNewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("myImageView/*");
                i.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(i, "Select Image"), 500);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            image.setImageURI(imageUri);

        }
    }


}
