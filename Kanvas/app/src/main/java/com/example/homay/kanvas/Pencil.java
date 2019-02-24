package com.example.homay.kanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Pencil extends View {
    Paint paintProps;
    Canvas canvasPaper;
    float positionX, positionY;

    public Pencil(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintProps = new Paint();
        canvasPaper = new Canvas();
        positionY = 0;
        positionX = 0;

    }

    public Pencil(Context context) {
        this(context,null);    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintProps.setColor(Color.BLACK);
        paintProps.setAntiAlias(true);
        paintProps.setStyle(Paint.Style.STROKE);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        positionX = event.getX();
        positionY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawOnTouch(positionX, positionY);
                return true;
        }

        return false;

    }

    public void drawOnTouch(float x, float y) {

        float pX, pY;
        pX = x;
        pY = y;

        canvasPaper.drawRect(100, 200, 200, 400, paintProps);
        invalidate();
        requestLayout();


    }

}
