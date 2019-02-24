package com.example.homay.addtextinimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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

public class ScaledView extends View {
    String TAG = "ScaledView";
    Fetcher fetcher;
    Uri imageURI;
    Bitmap globalBitmap;
    Paint p;
    float whatX = 100, whatY = 100;
    String mytext;
    Paint globalPaint;
    float viewMidX, viewMidY;
    boolean flagSave = false;
    boolean firstRun = true, stringNotNull = false;

    float viewX, viewY, imageX, imageY, imageAspectRatio, viewAspectRatio, scaleVal;
    float currentX = 50, currentY = 50;

    //movement
    float initialX = 0, initialY = 0;
    float movedX = 0;
    float movedY = 0;


    //save
    float scaleFactor = 0;
    String pathname = "";
    float imageStartLeft, imageStartTop, textPositionX, textPositionY, distanceFrom00X, distanceFrom00Y;

    public ScaledView(Context context, AttributeSet attrs) {
        super(context, attrs);
        globalPaint = new Paint();
        p = new Paint();
        viewX = 0;
        viewY = 0;
        imageX = 0;
        imageY = 0;
        imageAspectRatio = 0;
        viewAspectRatio = 0;
        scaleVal = 0;

    }


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


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        globalPaint.setColor(Color.BLACK);
        globalPaint.setStyle(Paint.Style.FILL);

        viewX = getMeasuredWidth();
        viewY = getMeasuredHeight();

        viewAspectRatio = viewX / viewY;


        if (firstRun && imageURI != null) {
            try {
                globalBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageURI);
                // canvas.drawBitmap(globalBitmap, globalBitmap.getWidth(), globalBitmap.getHeight(), globalPaint);
                Log.i(TAG, " " + globalBitmap.getHeight() + globalBitmap.getWidth());


                //    canvas.drawBitmap(globalBitmap, 0, 0, globalPaint);
                Bitmap here = scaleCenterCrop(globalBitmap, (int) viewY, (int) viewX);
                canvas.drawBitmap(here, 0, 0, globalPaint);

                imageX = globalBitmap.getWidth();
                imageY = globalBitmap.getHeight();


                imageAspectRatio = imageX / imageY;

                p.setTextSize(50 / scaleFactor);
                p.setColor(Color.BLACK);
                Log.i(TAG, "scale factor" + scaleFactor);

                p.setStyle(Paint.Style.STROKE);

                canvas.drawText("lost", 50, 50, p);
                Log.i(TAG, "Drawn");

                firstRun = false;

            } catch (IOException e) {
                e.printStackTrace();

            }


        }

        fetchData();
        globalPaint.setTextSize(50);
        if (stringNotNull) {
            canvas.drawText(mytext, currentX + movedX, currentY + movedY, globalPaint);

            currentX = currentX + movedX;

            currentY = currentY + movedY;
            textPositionX = currentX;
            textPositionY = currentY;


            movedX = 0;
            movedY = 0;

        }


        if (flagSave) {

            distanceFrom00X = textPositionX - imageStartLeft;
            distanceFrom00Y = textPositionY - imageStartTop;

            canvas.drawBitmap(globalBitmap, 0, 0, globalPaint);
            globalPaint.setTextSize(50 / scaleFactor);
            canvas.drawText(mytext, distanceFrom00X / scaleFactor, distanceFrom00Y / scaleFactor, globalPaint);
            flagSave = false;
            //saveFile s = new saveFile();
            // s.execute();

        }

//end of onDraw
    }

    public Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.min(xScale, yScale);
        scaleFactor = scale;

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;
        imageStartLeft = left;
        imageStartTop = top;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);


        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());

        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;

    }


    private Uri getImageLocation() {
        return imageURI;
    }

    public void setImageLocation(Uri uri) {
        this.imageURI = uri;
        firstRun = false;


        invalidate();
    }

    public void setDataFetcher(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

    public void fetchData() {
        fetcher.onFetchAspectRatio(viewAspectRatio, imageAspectRatio);
        fetcher.onFetchImageDimension(imageX, imageY);
        fetcher.onFetchScaledVal(scaleVal);
        fetcher.onFetchViewDimension(viewX, viewY);
    }

    public void setText(String string) {
        mytext = string;
        invalidate();
        stringNotNull = true;
    }


    public void saveImage() {

        flagSave = true;
        invalidate();


    }

    public void scanSavedFile() {
        MediaScannerConnection.scanFile(getContext(), new String[]{pathname}, new String[]{"image/png"}, null);
    }

    public class saveFile extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            int counter = new Random().nextInt(50 - 20 + 1) + 20;

            Canvas canvas = new Canvas(globalBitmap);
            //canvas.drawText(myText, finalPositionX+movedX,finalPositionY+movedY, paintText);

            draw(canvas);

            try {
                File file = Environment.getExternalStorageDirectory();
                String path = file.getAbsolutePath();
                pathname = path + "/random" + counter++ + ".jpg";
                FileOutputStream fileOutputStream = new FileOutputStream(new File(pathname));


                globalBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();

                scanSavedFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

    }
    //end of class
}
