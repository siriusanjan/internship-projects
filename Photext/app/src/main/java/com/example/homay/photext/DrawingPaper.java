package com.example.homay.photext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class DrawingPaper extends View {

    private String TAG = "DrawingPaper";

    //Image dimensions
    float imageHeight; //height
    float imageWidth; //width

    //View dimensions
    float viewHeight; //height
    float viewWidth; //width

    //Scaling
    float scaleFactor; //scale factor
    float imageStartX; //starting X position of scaled image in view
    float imageStartY; //starting Y position of scaled image in view

    //Data
    String stringData; //holds text to draw on the canvas
    String imageL; //holds saved image path
    int textSize; //holds text size;

    //Callbacks
    ViewCallback viewCallback; //Callback after file is saved


    //Paint objects
    Paint textPainter;  //props for drawing text objects
    Paint bitmapPainter; //props for drawing bitmap objects

    //Bitmap
    Bitmap bitmapSource;  //Global bitmap of the user selected file


    //Co-ordinates
    float movedX = 0; //Text X moved position; set to 0 by default
    float movedY = 0; //Text Y moved position; set to 0 by default

    float currentX = 0; //Text position by default; later set to image center
    float currentY = 0; //Text position by default; later set to image center

    float initialX = 0; //Used for onTouchEvent
    float initialY = 0; //Used for onTouchEvent

    private boolean measureViewDimension = true; //Flag to check some lines execute only once inside onDraw method


    float textOriginalPX = 0;  //Stores text original X position on custom view
    float textOriginalPY = 0;   //Stores text original Y position on custom view

    Rect imageRect; //Rectangle of size image when scaled


    public DrawingPaper(Context context, AttributeSet attrs) {
        super(context, attrs);

        imageHeight = 0;
        imageWidth = 0;
        viewHeight = 0;
        viewWidth = 0;
        textPainter = new Paint();
        stringData = "";
        bitmapPainter = new Paint();
        textSize = 30;



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        try {
            canvas.drawBitmap(bitmapSource, null, imageRect, bitmapPainter);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //TEXT DRAWING STUFF FROM HERE
        textPainter.setTextSize(textSize);
        textPainter.setStyle(Paint.Style.STROKE);
        textPainter.setColor(Color.BLACK);
        textPainter.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(stringData, currentX + movedX, currentY + movedY, textPainter);
        currentX = currentX + movedX;
        currentY = currentY + movedY;

        //store positions of text for scaling later
        textOriginalPX = currentX;
        textOriginalPY = currentY;


    }


    //detects touch for changing text position

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                initialY = event.getY();
                return true;


            case MotionEvent.ACTION_MOVE:
                float currentPosX = event.getX();
                float currentPosY = event.getY();

                float movedX_ = currentPosX - initialX;
                float movedY_ = currentPosY - initialY;
                movedX = movedX_;
                movedY = movedY_;
                initialX = event.getX();
                initialY = event.getY();

                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                invalidate();
                return true;


        }

        return false;
    }


    //calculates view height and width
    private void setViewDimension() {
        viewHeight = getMeasuredHeight();
        viewWidth = getMeasuredWidth();
        Log.i("View dimensions", viewHeight + " " + viewWidth);


    }


    public void setImageDimension(String image) {

        try {
            imageL = image;
            bitmapSource = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(image));
            post(new Runnable() {
                @Override
                public void run() {

                    imageHeight = bitmapSource.getHeight();
                    imageWidth = bitmapSource.getWidth();

                    setViewDimension();
                    calculateScaleFactor();

                    //bitmap painter

                    currentX = viewWidth / 2; //text position
                    currentY = viewHeight / 2;

                    bitmapPainter.setColor(Color.GREEN);
                    bitmapPainter.setStyle(Paint.Style.STROKE);

                    //scaling
                    float scaledWidth = bitmapToViewX(imageWidth);
                    float scaledHeight = bitmapToViewY(imageHeight);



                    imageRect = new Rect((int) imageStartX, (int) imageStartY, (int) (imageStartX + scaledWidth), (int) (imageStartY + scaledHeight));

                    invalidate();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    //function called by textwatcher; in any change in edittext view
    public void setText(String s) {

        this.stringData = s;
        invalidate();
    }


    public void saveBitmap() {
        SaveImage saveImage = new SaveImage();
        saveImage.doInBackground();
        saveImage.execute();

    }


    public void setViewCallBack(ViewCallback viewCallBack) {

        this.viewCallback = viewCallBack;
    }


    public class SaveImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {


            Bitmap myBitmap = bitmapSource.copy(Bitmap.Config.ARGB_8888, true);

            Canvas canvasFinal = new Canvas(myBitmap);
            //  draw(canvasFinal);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(textSize / scaleFactor);
            float textPositionX = textOriginalPX - imageStartX;
            float textPositionY = textOriginalPY - imageStartY;


            canvasFinal.drawText(stringData, (int) (textPositionX / scaleFactor), (int) (textPositionY / scaleFactor), paint);

            //saving
            int counter = new Random().nextInt();

            File file = Environment.getExternalStorageDirectory();
            String path = file.getAbsolutePath();
            String pathname = path + "/random" + counter + ".jpg";
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(new File(pathname));
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(getContext(), new String[]{pathname}, new String[]{"image/png"}, null);

            viewCallback.OnImageSave(pathname);
            Log.i("myimagepath", pathname);


            return null;
        }
    }

    public void calculateScaleFactor() {
        float xScale = viewWidth / imageWidth;
        float yScale = viewHeight / imageHeight;
        scaleFactor = Math.min(xScale, yScale);

    }

    public float bitmapToViewX(float bitmapX) {
        float scaledWidth = scaleFactor * bitmapX;
        imageStartX = (viewWidth - scaledWidth) / 2;

        return scaledWidth;

    }


    public float bitmapToViewY(float bitmapY) {

        float scaledHeight = scaleFactor * bitmapY;
        imageStartY = (viewHeight - scaledHeight) / 2;

        return scaledHeight;
    }

    public void setTextSize(int size) {
        textSize = size;
        invalidate();
    }

}

