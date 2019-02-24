package com.example.homay.cropandrotate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SeekBarActivity extends AppCompatActivity {
    float rotationDegree, scaleSize;
    ImageView imageView, imageViewLineFirst, imageViewLineSecond;
    SeekBar seekBar;
    float rotation = 0F;
    Button buttonBitmap, buttonChop;
    Bitmap bitmapOriginal, bitmapRotated, bitmapChopped;
    ImageView imageViewResultContainer;
    Matrix matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        imageViewLineFirst = findViewById(R.id.horizontal_line_first);
        imageViewLineSecond = findViewById(R.id.horizontal_line_second);
        buttonBitmap = findViewById(R.id.button_create_bitmap);
        buttonChop = findViewById(R.id.button_chop);

        imageViewResultContainer = findViewById(R.id.imageview_result_container);

        imageViewLineFirst.setVisibility(View.INVISIBLE);

        imageView = findViewById(R.id.imageview_container);
        seekBar = findViewById(R.id.seekbar_zoom_seekbar);
        imageView.setScaleY((float) ((1 / 100.0f) + 1.2));

        seekBar.setMax(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imageViewLineFirst.setVisibility(View.VISIBLE);
                float scale = (float) ((progress / 100.0f) + 1.2);
                imageView.setScaleX(scale);
                imageView.setScaleY(scale);
                rotation = progress / 2;
                imageView.setRotation(rotation);
                rotationDegree = rotation;
                scaleSize = scale;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        imageViewLineFirst.setVisibility(View.INVISIBLE);

        //checking heights and width of image and drawable

        //image height and width
        final float imageHeight = imageView.getDrawable().getIntrinsicHeight();
        final float imageWidth = imageView.getDrawable().getIntrinsicWidth();
        Log.i("Drawable height/width", "Size" + imageHeight + imageWidth);

//screen height and width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        int displayDpi = displayMetrics.densityDpi;
        int displayDensity = (int) displayMetrics.density;

        Log.i("Display h & W", "Val" + displayHeight + displayWidth);
        Log.i("DPI and den ", "DPI " + displayDpi + "densi " + displayDensity);

        //create bitmap

        bitmapOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.forest);
        //Cropping stuff


        matrix = new Matrix();

        //create bitmap
        buttonBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                matrix.postRotate(rotationDegree);

                bitmapRotated = Bitmap.createBitmap(bitmapOriginal, 0, 0, bitmapOriginal.getWidth(), bitmapOriginal.getHeight(), matrix, true);
                imageViewResultContainer.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageViewResultContainer.setImageBitmap(bitmapRotated);
            }
        });

        //chop
        buttonChop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                float selectedHeight = imageHeight - rotationDegree;
                float selectedWidth = imageWidth - rotationDegree;
                float startX = imageWidth / ((rotationDegree / 2) * (rotationDegree / 2));
                float startY = imageHeight / ((rotationDegree / 2) * (rotationDegree / 2));


//float startX = (imageWidth/4) - rotationDegree;
//float startY = (imageHeight/4)- rotationDegree;
                float reqWidth = (imageWidth / 2) + rotationDegree;
                float reqHeight = (imageHeight / 2) + rotationDegree;
                //  bitmapChopped = Bitmap.createBitmap(bitmapRotated,0,0,bitmapRotated.getWidth(),bitmapRotated.getHeight());
                bitmapChopped = Bitmap.createBitmap(bitmapRotated, (int) startX, (int) startY, (int) reqWidth, (int) reqHeight);
//        SaveFile newFile = new SaveFile();
//        newFile.execute();
                imageViewResultContainer.setImageBitmap(bitmapChopped);
            }
        });

    }

    public class SaveFile extends AsyncTask<Void, Void, Void> {


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... voids) {


            File path = Environment.getExternalStorageDirectory();
            File file = new File(path, "myimage.jpeg");

            try (FileOutputStream out = new FileOutputStream(file)) {
                bitmapChopped.compress(Bitmap.CompressFormat.PNG, 90, out);
                Log.i("Seekbar", "Successfullysaved");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
