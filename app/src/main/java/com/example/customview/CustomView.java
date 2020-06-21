package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    private Rect mRect;
    private Paint mSquarePaint, mCirclePaint;
    private static final int SQUARE_MARGIN = 10;
    private static final int SQUARE_WIDTH_DEF = 100;
    private int mSquareSize = 100;
    private int mSquareColor = 0;
    private float mCircleX, mCircleY;
    private float mCircleRadius;


    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

        mSquarePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.GREEN);
        if (attrs == null) {
            return;
        }
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
        mSquareColor = ta.getColor(R.styleable.CustomView_square_color, Color.BLUE);
        mSquareSize = ta.getDimensionPixelSize(R.styleable.CustomView_square_size, SQUARE_WIDTH_DEF);
        mSquarePaint.setColor(mSquareColor);
        mRect = new Rect();

        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRect.left = SQUARE_MARGIN;
        mRect.top = SQUARE_MARGIN;
        mRect.right = mRect.left + mSquareSize;
        mRect.bottom = mRect.top + mSquareSize;
        canvas.drawRect(mRect, mSquarePaint);


        mCircleRadius = 100f;
        if(mCircleX == 0 && mCircleY == 0) {
            mCircleX = mSquareSize + (getWidth() - mSquareSize) / 2;
            mCircleY = getHeight() / 3;
        }
        canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, mCirclePaint);
    }

    public void swapColor() {
        mSquarePaint.setColor(mSquarePaint.getColor() == mSquareColor ? Color.BLACK : mSquareColor);
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                if(mRect.left < x && mRect.right > x) {
                    if(mRect.top < y && mRect.bottom> y) {
                        swapColor();
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                float x_mv = event.getX();
                float y_mv = event.getY();
                double dx = Math.pow((x_mv - mCircleX), 2);
                double dy = Math.pow((y_mv - mCircleY), 2);
                if (dx + dy < Math.pow(mCircleRadius, 2)) {
                    mCircleX = x_mv;
                    mCircleY = y_mv;
                    postInvalidate();
                }
                return true;
        }
        return value;
    }
}
