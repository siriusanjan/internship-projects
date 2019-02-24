package com.example.homay.kanvastouch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class KustomView extends View {
    Canvas canvasCC;
    String textX, textY;
    Context context;
    Paint paintProps;
    Paint paintText;
    Path pathMaker;
    float screenWidth, screenHeight, valX, valY;
    float pathX, pathY;
    float storedX, storedY;

    public KustomView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
        pathMaker = new Path();
        paintText = new Paint();
        pathX = 0;
        pathY = 0;
//storedX = 0;
//storedY = 0;
        canvasCC = new Canvas();
        paintProps = new Paint();
        screenHeight = getMeasuredHeight();
        screenWidth = getMeasuredWidth();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        screenHeight = getMeasuredHeight();
        screenWidth = getMeasuredWidth();
        paintProps.setColor(Color.RED);
        paintProps.setStyle(Paint.Style.STROKE);
        paintProps.setStrokeWidth(10);
        paintProps.setStrokeCap(Paint.Cap.ROUND);

        paintProps.setAntiAlias(true);
        paintProps.setDither(true);

        canvasCC = canvas;


        canvasCC.drawRect(100, 100, 200, 200, paintProps);
    //    canvasCC.drawRect(500, 100, 700, 200, paintProps);


        Log.i("val xy", storedX + " " + storedY);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case (MotionEvent.ACTION_DOWN):
                float action_down_valX = event.getX();
                float action_down_valY = event.getY();
                if (action_down_valX < 200 && action_down_valX > 100 && action_down_valY > 100 && action_down_valY < 200) {
                    context.startActivity(new Intent(this.context, MainActivity.class));


                } else {

                    pathMaker.moveTo(action_down_valX, action_down_valY);
                    pathX = action_down_valX;
                    pathY = action_down_valY;
                    //drawPath(pathMaker);

                }
                return true;


            case (MotionEvent.ACTION_MOVE):

float                 action_move_valX = event.getX();
     float           action_move_valY = event.getY();
                textX = String.valueOf(action_move_valX);
                textY = String.valueOf(action_move_valY);
                pathMaker.moveTo(pathX, pathY);
                pathMaker.lineTo(action_move_valX, action_move_valY);
                drawPath(pathMaker);
                pathX  = action_move_valX;
                pathY = action_move_valY;


                return true;


            case (MotionEvent.ACTION_UP):
                pathX = event.getX();
                pathY= event.getY();

                return true;

        }

        return false;
    }

    private void drawRect(float x, float y) {
        storedX = x;
        storedY = y;
        canvasCC.drawRect(x - 50, y - 50, x, y, paintProps);
        invalidate();
        requestLayout();
    }

    private void drawPath(Path path) {

        Path myPath;
        myPath = path;
        canvasCC.drawPath(myPath, paintProps);
        paintText.setTextSize(50);
paintText.setAntiAlias(true);
        paintText.setLinearText(true);
        canvasCC.drawText(pathX+ "   " +pathY,200,200,paintText);
        invalidate();
        requestLayout();

    }

}
