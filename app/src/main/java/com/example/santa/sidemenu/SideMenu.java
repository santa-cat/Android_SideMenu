package com.example.santa.sidemenu;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by santa on 16/7/11.
 */
public class SideMenu extends ViewGroup {
    private String DEBUG = "SideMenu_dbg";
    private ListView mListView;
    private ImageView mImageView;
    private Paint mPaint = new Paint();
    private Paint mPaintIcon = new Paint();

    //rect
    private int mRecHeight  = 550;
    private int mRectLenght = 50;
    private int mLineWidth = 15;
    private int mColor = 0xffF8DB01;
    private int mListColor = 0xff444444;
    private RectF mRectF;

//    private int mMax
    public final static int ICON_FORK = 1;
    public final static int ICON_ARROW = 2;
    private int mIconState = ICON_ARROW;
    float mDensity;

    public SideMenu(Context context) {
        this(context, null);
    }

    public SideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SideMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mDensity = metrics.density;

        setBackgroundColor(0x00000000);

        mListView = new ListView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mListView.setLayoutParams(layoutParams);
        mListView.setBackgroundColor(mListColor);
        mListView.setDividerHeight(0);
        addView(mListView);

        mImageView = new ImageView(context);
        layoutParams = new RelativeLayout.LayoutParams(getPxFromDp(75), getPxFromDp(75));
        mImageView.setPadding(getPxFromDp(10), getPxFromDp(10), getPxFromDp(10), getPxFromDp(10));
        mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.music));
        mImageView.setLayoutParams(layoutParams);
        addView(mImageView);

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);

        mPaintIcon.setAntiAlias(true);
        mPaintIcon.setStyle(Paint.Style.STROKE);
        mPaintIcon.setColor(mListColor);
        mPaintIcon.setStrokeWidth(getPxFromDp(3));

        mRecHeight  = getPxFromDp(mRecHeight);
        mRectLenght = getPxFromDp(mRectLenght);
        mLineWidth = getPxFromDp(mLineWidth);

        mRectF = new RectF(0, mRecHeight, mRectLenght, mRectLenght+mRecHeight);

    }

    private int getPxFromDp(int dp) {
        return (int) (mDensity*dp);
    }



    public void setAdapter(ListAdapter adapter) {
        if(mListView != null) {
            mListView.setAdapter(adapter);
        }
    }

//    public void set

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(null != mListView){
            measureChildWithMargins(mListView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        if (null != mImageView) {
            measureChild(mImageView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int paddingLeft   = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        if(null != mListView) {
            MarginLayoutParams lp = (MarginLayoutParams) mListView.getLayoutParams();
            int left = mRectLenght + mLineWidth + paddingLeft + lp.leftMargin;
            int top = lp.topMargin + paddingTop;
            int right = left + mListView.getMeasuredWidth();
            int bottom = top + mListView.getMeasuredHeight();

            mListView.layout(left, top, right, bottom);
        }

        if (null != mImageView) {
            int left = mRectLenght + mLineWidth + paddingLeft;
            int top = getMeasuredHeight() - paddingBottom - mImageView.getMeasuredHeight();
            int right = left + mImageView.getMeasuredWidth();
            int bottom = top + mImageView.getMeasuredHeight();

            mImageView.layout(left, top, right, bottom);

            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(mImageView, "rotation", 0, 360);
            valueAnimator.setDuration(5000);
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.start();

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
    }

    private void drawRect(Canvas canvas) {
        int bottom = getHeight() - getPaddingBottom();
        mRectF = new RectF(0, bottom-mRectLenght, mRectLenght, bottom);

        canvas.drawRect(mRectF, mPaint);
        RectF rectFLine = new RectF(mRectLenght, 0, mRectLenght + mLineWidth, getHeight());
        canvas.drawRect(rectFLine, mPaint);
        if (mIconState == ICON_ARROW) {
            drawArrow(canvas);
        } else {
            drawFork(canvas);
        }
    }

    private void drawArrow(Canvas canvas) {
        float rectWidth = mRectF.right - mRectF.left;
        canvas.drawLine(rectWidth/3+rectWidth/12 , mRectF.top+rectWidth/2, rectWidth*1/2+rectWidth/12, mRectF.top+rectWidth/3, mPaintIcon);
        canvas.drawLine(rectWidth/3+rectWidth/12, mRectF.top+rectWidth/2, rectWidth*1/2+rectWidth/12, mRectF.top+rectWidth*2/3, mPaintIcon);
    }

    private void drawFork(Canvas canvas) {
        float rectWidth = mRectF.right - mRectF.left;
        canvas.drawLine(rectWidth/3+rectWidth/24 , mRectF.top+rectWidth/3+rectWidth/24, rectWidth*2/3-rectWidth/24, mRectF.top+rectWidth*2/3-rectWidth/24, mPaintIcon);
        canvas.drawLine(rectWidth/3+rectWidth/24, mRectF.top+rectWidth*2/3-rectWidth/24, rectWidth*2/3-rectWidth/24, mRectF.top+rectWidth/3+rectWidth/24, mPaintIcon);
    }

    public void setIcon(int state) {
        mIconState = state;
        invalidate();
    }

    public int getRecfLenght () {
        return mRectLenght + mLineWidth;
    }

    public RectF getRecF() {
        return mRectF;
    }

    private void Log(String s) {
        Log.d(DEBUG, s);
    }

}
