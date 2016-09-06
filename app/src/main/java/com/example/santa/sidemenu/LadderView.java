package com.example.santa.sidemenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by santa on 16/7/12.
 */
public class LadderView extends View implements View.OnClickListener{
    private Paint mPaintOut = new Paint();
    private Paint mPaintIcon = new Paint();
    private Path mPath = new Path();
    public static final int STYLE_LEFT = 1;
    public static final int SYYLE_RIGHT = 2;
    private int viewStyle = STYLE_LEFT;

    public static final int ICON_PLAY = 2;
    public static final int ICON_PLUS = 1;
    private int iconStyle = ICON_PLUS;

    private int unPressedcolor = 0xff444444;
    private int pressedcolor = 0xffF8DB01;

    private int iconColor = Color.WHITE;
    private int iconPressedColor = 0xff444444;

    public LadderView(Context context) {
        this(context, null);
    }

    public LadderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LadderView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LadderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LadderView);

        viewStyle = a.getInteger(R.styleable.LadderView_lv_style, viewStyle);
        iconStyle = a.getInteger(R.styleable.LadderView_lv_icon, iconStyle);

        a.recycle();
        mPaintOut.setAntiAlias(true);
        mPaintOut.setStyle(Paint.Style.FILL);
        mPaintOut.setColor(unPressedcolor);

        mPaintIcon.setAntiAlias(true);
        mPaintIcon.setColor(iconColor);
        if (iconStyle == ICON_PLUS) {
            mPaintIcon.setStyle(Paint.Style.STROKE);
            mPaintIcon.setStrokeWidth(10);
        } else {
            mPaintIcon.setStyle(Paint.Style.FILL);
        }
        setOnClickListener(this);
    }


    private void clickChangeColor(){
        if (pressedcolor == mPaintOut.getColor()) {
            mPaintOut.setColor(unPressedcolor);
            mPaintIcon.setColor(iconColor);
        } else {
            mPaintOut.setColor(pressedcolor);
            mPaintIcon.setColor(iconPressedColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();

        //draw outSide
        mPath.reset();
        if (viewStyle == STYLE_LEFT) {
            mPath.moveTo(0, height / 5);
            mPath.lineTo(0, height - height / 5);
            mPath.lineTo(width, height);
            mPath.lineTo(width, 0);
            mPath.close();
        } else {
            mPath.moveTo(0, 0);
            mPath.lineTo(0, height);
            mPath.lineTo(width, height - height/5);
            mPath.lineTo(width, height/5);
            mPath.close();
        }
        canvas.drawPath(mPath, mPaintOut);

        //drawIcon
        if (iconStyle == ICON_PLUS) {
            canvas.drawLine(width/2, height/3, width/2, height*2/3, mPaintIcon);
            canvas.drawLine(width/3, height/2, width*2/3, height/2, mPaintIcon);
        } else {
            mPath.reset();
            mPath.moveTo(width/3 ,height/3);
            mPath.lineTo(width/3, height*2/3);
            mPath.lineTo(width*2/3, height/2);
            mPath.close();
            canvas.drawPath(mPath, mPaintIcon);
        }

    }

    @Override
    public void onClick(View view) {
        clickChangeColor();
        invalidate();
    }
}
