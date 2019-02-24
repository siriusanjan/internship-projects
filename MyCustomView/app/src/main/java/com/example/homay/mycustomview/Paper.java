package com.example.homay.mycustomview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Paper extends View {
private int circleCol, labelCol;
private Canvas myCanvas;
    private Paint painter;
private String circleText;
private Paint circlePaint; //Paint class tells how to draw. While Canvas class tells what to draw.
    private Paint linePaint, rectanglePaint;
    int userAction;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


float x = event.getX();
float y = event.getY();


        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                painter.setStyle(Paint.Style.FILL);
                painter.setColor(Color.BLACK);
                painter.setAntiAlias(true);
                myCanvas.drawRect(x - 100, y - 100, y + 100, x + 100, painter);
                invalidate();
               requestLayout();

        return true;



        }

        return true;

    }

    public Paper(Context context, AttributeSet attrs) {
        super(context, attrs);
        circlePaint = new Paint();
        linePaint = new Paint();
        rectanglePaint = new Paint();
        painter = new Paint();


        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Paper, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            circleText = a.getString(R.styleable.Paper_circleLabel);
            circleCol = a.getInteger(R.styleable.Paper_circleColor, 0);
            labelCol = a.getInteger(R.styleable.Paper_labelColor, 0);


        }finally {
            a.recycle();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        myCanvas = canvas;
       // super.onDraw(canvas);
        //get info about the circle
        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;

        //calculate radius of circle
        //half of height or width, whichever is smaller
        //subtract 10 so that it gets space

//        int radius = viewHeightHalf < viewWidthHalf ? ((viewHeightHalf/2)-10) : ((viewWidthHalf/2)-10);

    int radius = 0;
        if (viewWidthHalf > viewHeightHalf) {
            radius = viewHeightHalf - 10;

        }
        else {
            radius = viewWidthHalf - 10;
        }

        //setting properties for painting
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleCol);
        circlePaint.setTextAlign(Paint.Align.CENTER);
        circlePaint.setTextSize(50);


       // canvas.drawCircle(viewWidthHalf,viewHeightHalf, radius, circlePaint);

circlePaint.setColor(labelCol);
        //draw text
        canvas.drawText(circleText, viewWidthHalf, viewHeightHalf, circlePaint);








        //painting a line
        linePaint.setColor(labelCol);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(viewWidthHalf,viewHeightHalf,viewWidthHalf,viewHeightHalf-radius ,linePaint);

        rectanglePaint.setColor(Color.BLUE);
        rectanglePaint.setStyle(Paint.Style.FILL);
        rectanglePaint.setAntiAlias(true);
canvas.drawRect(50,50,100,100,rectanglePaint);

 //       canvas.drawRect(10,(float)( (viewWidthHalf+viewWidthHalf) -10),10,(float)(viewHeightHalf+viewHeightHalf-10),rectanglePaint);


    //end of onDraw
    }



    public int getCircleCol() {
        return circleCol;
    }

    public void setCircleCol(int circleCol) {
        this.circleCol = circleCol;
        //redraw the view
        //this will also call the onDraw method again
        invalidate();
        requestLayout();
    }

    public int getLabelCol() {
        return labelCol;
    }

    public void setLabelCol(int labelCol) {
        this.labelCol = labelCol;
        //redraw the view
        invalidate();
        requestLayout();

    }

    public void setCircleText(String circleText) {
        this.circleText = circleText;
        //redraw the view
        invalidate();
        requestLayout();
    }


    //end of class
}