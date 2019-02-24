package com.example.homay.kanvastouch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class MyCustomView extends View {
    Canvas canvasMain;
    Context context;
    float screenWidth, screenHeight;
    Paint paintMain;
float touchX, touchY;
// @androidx.annotation.Nullable
    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        canvasMain = new Canvas();
        paintMain = new Paint();
        screenHeight = getMeasuredHeight();
screenWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        screenHeight = getMeasuredHeight();
        screenWidth = getMeasuredWidth();
        canvasMain = canvas;
        paintMain.setColor(Color.BLACK);
        paintMain.setAntiAlias(true);
        paintMain.setStyle(Paint.Style.STROKE);
//        canvasMain.drawRect(50, 50, 100, 100,paintMain);
canvasMain.drawRect(screenWidth-50,0,screenWidth,50,paintMain);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();

                if (touchX > (screenWidth - 50) && touchY < 50) {
                    raiseFlag();
                }
                else
                {

                drawRect(touchX, touchY);
        }}



        return false;



    }

    public  void raiseFlag() {
        context.startActivity(new Intent(context, DrawActivity.class));

    }


    private void drawRect(float touchX, float touchY) {

        canvasMain.drawRect(touchX - 10, touchY - 10, touchX + 30, touchY + 30, paintMain);
        invalidate();
        requestLayout();
    }


}

