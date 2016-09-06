package com.example.santa.sidemenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by santa on 16/7/13.
 */
public class BorderTextView extends TextView {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private int mPaddingExpand;

    public BorderTextView(Context context) {
        this(context, null);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        final float density = context.getResources().getDisplayMetrics().density;

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3*density);
        mPaint.setColor(Color.BLACK);

        mPaddingExpand = (int)(10*density);
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom() + mPaddingExpand);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw border
        int height = getHeight();
        int width = getWidth();
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(0, height - mPaddingExpand);
        mPath.lineTo(width/2 - mPaddingExpand, height - mPaddingExpand);
        mPath.lineTo(width/2, height);
        mPath.lineTo(width/2 + mPaddingExpand, height - mPaddingExpand);
        mPath.lineTo(width, height - mPaddingExpand);
        mPath.lineTo(width, 0);
        mPath.close();

        canvas.drawPath(mPath, mPaint);

    }
}
