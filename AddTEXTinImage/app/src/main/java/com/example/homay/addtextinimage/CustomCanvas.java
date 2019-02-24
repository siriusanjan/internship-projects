package com.example.homay.addtextinimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class CustomCanvas extends View {
    //Paint and canvas
    Paint paintGlobal;
    Bitmap bm;
    Paint paintText;
    Bitmap bitmapGlobal;

    //Measurements
    float midX, midY, viewX, viewY;

    //Text
    String myText = "";
    int textSize = 30;
    //save
    String pathname;
    //Menu
    int seekBarValChanged;

    //text movement

    float finalPositionX = 50, finalPositionY = 50, currentX = 0, currentY = 0, movedX = 0, movedY = 0, downX = 0, downY = 0, jx, jy;

    public CustomCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintGlobal = new Paint();
        paintText = new Paint();

        bitmapGlobal = BitmapFactory.decodeResource(getResources(), R.drawable.sample_image);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getMeasurements();
        String text = myText;
        //set props
        paintGlobal.setColor(Color.BLACK);
        paintGlobal.setStyle(Paint.Style.STROKE);
        paintGlobal.setAntiAlias(true);
        paintText.setColor(Color.GREEN);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setAntiAlias(true);
        paintText.setTextSize(textSize);
        paintText.setTextAlign(Paint.Align.CENTER);

        //draw image on canvas
        canvas.drawBitmap(bitmapGlobal, 0, 100, paintGlobal);


        //draw text

        canvas.drawText(text, finalPositionX + movedX, finalPositionY + movedY, paintText);


    }

    private void getMeasurements() {
        viewX = getMeasuredWidth();
        viewY = getMeasuredHeight();
        midX = viewX / 2;
        midY = viewY / 2;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentX = event.getX();
                currentY = event.getY();
                downX = currentX;
                downY = currentY;

                return true;

            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                currentY = event.getY();
                movedX = currentX - downX;
                movedY = currentY - downY;


                invalidate();

                return true;


            case MotionEvent.ACTION_UP:
                currentX = event.getX();
                currentY = event.getY();
                finalPositionX = finalPositionX + movedX;
                finalPositionY = finalPositionY + movedY;
                movedX = 0;
                movedY = 0;


                return true;
        }

        return false;

    }


    public boolean saveImage() {
        bm = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        saveFile s = new saveFile();
        s.execute();
        return true;
    }

    public void setText(String string) {
        this.myText = string;
        invalidate();
    }

    public void setChangedValues(int seekBarValue) {
        this.seekBarValChanged = seekBarValue;
        invalidate();

    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public void reset() {
        myText = "";

        int textSize = 30;

        invalidate();

    }

    public void scanSavedFile() {
        MediaScannerConnection.scanFile(getContext(), new String[]{pathname}, new String[]{"image/png"}, null);
    }

    public class saveFile extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            int counter = new Random().nextInt(50 - 20 + 1) + 20;

            Canvas canvas = new Canvas(bm);
            //canvas.drawText(myText, finalPositionX+movedX,finalPositionY+movedY, paintText);

            draw(canvas);

            try {
                File file = Environment.getExternalStorageDirectory();
                String path = file.getAbsolutePath();
                pathname = path + "/random" + counter++ + ".jpg";
                FileOutputStream fileOutputStream = new FileOutputStream(new File(pathname));


                bm.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();

                scanSavedFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

    }


}


