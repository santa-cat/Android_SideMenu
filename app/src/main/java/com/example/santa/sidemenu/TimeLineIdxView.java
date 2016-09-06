package com.example.santa.sidemenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by santa on 16/7/14.
 */
public class TimeLineIdxView extends View {
    private Paint mPaint = new Paint();
    private int mColorLine = 0xffF8DB01;
    private int mColorText = 0xff666666;
    private String text;
    private int radius;

    public TimeLineIdxView(Context context) {
        this(context, null);
    }

    public TimeLineIdxView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineIdxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final float density = context.getResources().getDisplayMetrics().density;

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3*density);
        mPaint.setColor(mColorLine);
        mPaint.setTextSize(28);

        radius = (int)(density*5);
    }

    public void setText(String string) {
        text = string;
        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        canvas.drawLine(width/3, 0, width/3, height, mPaint);
        canvas.drawLine(width/3, height/4, width*3/4, height/4, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width/3, height/4, radius*3/2, mPaint);
        canvas.drawCircle(width*3/4, height/4, radius, mPaint);
        mPaint.setColor(mColorText);
        canvas.drawText(text, 0, height/4, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(width/3, height/4, radius, mPaint);
        mPaint.setColor(mColorLine);

    }


}
