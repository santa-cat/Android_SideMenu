package com.example.santa.sidemenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by santa on 16/7/14.
 */
public class DescribeImage extends LinearLayout {
    private int mMainTextColor = Color.BLACK;
    private int mOtherTextColor = 0xff666666;
    private int mBackColor = 0xffdedede;
    public ImageView mImageView;
    private LinearLayout mTextContainer;
    private Context mContext;
    private TextView nameTv;
    private TextView nameSinger;
    private TextView timeTv;
    private int mTextSize = 12;
    private Paint mPaint = new Paint();


    public DescribeImage(Context context) {
        this(context, null);
    }

    public DescribeImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DescribeImage(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DescribeImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setBackgroundColor(0x00000000);
        mContext = context;
        final float density = context.getResources().getDisplayMetrics().density;

        mImageView = new ImageView(context);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mImageView.setLayoutParams(layoutParams);
        addView(mImageView);

        mTextContainer = new LinearLayout(context);
        layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mTextContainer.setOrientation(VERTICAL);
        mTextContainer.setLayoutParams(layoutParams);
        mTextContainer.setBackgroundColor(mBackColor);
        mTextContainer.setPadding((int)(5*density) ,(int)(10*density), 0, 0 );
        addView(mTextContainer);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2);

        nameTv = new TextView(mContext);
        nameTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        nameTv.setTextColor(Color.BLACK);
        nameTv.setLayoutParams(layoutParams);
        mTextContainer.addView(nameTv);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        nameSinger = new TextView(mContext);
        nameSinger.setText("歌手  周杰伦");
        nameSinger.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize-3);
        nameSinger.setTextColor(mOtherTextColor);
        nameSinger.setLayoutParams(layoutParams);
        mTextContainer.addView(nameSinger);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        timeTv = new TextView(mContext);
        timeTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize-3);
        timeTv.setTextColor(mOtherTextColor);
        timeTv.setLayoutParams(layoutParams);
        mTextContainer.addView(timeTv);


        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffF8DB01);

    }

    public ImageView getImage() {
        //        mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.privateinvestocat));
        return mImageView;
    }

    public void setmImage(String url, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(url, mImageView, options);
    }

    public void setText(String name, String time) {
        nameTv.setText(name);
        timeTv.setText("发布  "+time);

        Log.d("debug", "width = "+mTextContainer.getWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRect(0, (mTextContainer.getTop() - getTop())*2 / 3 ,mTextContainer.getLeft()/2,mTextContainer.getTop() , mPaint);
//        canvas.drawRect(0, mTextContainer.getTop(), mImageView.getLeft(),mTextContainer.getTop() + mTextContainer.getHeight()/2, mPaint);

//        canvas.drawRect(mTextContainer.getLeft(), mTextContainer.getBottom(), mTextContainer.getRight(),mTextContainer.getBottom() + getPaddingEnd()/5, mPaint);
        canvas.drawRect(mTextContainer.getRight(), mTextContainer.getTop(), mTextContainer.getRight() + getPaddingRight()/10, mTextContainer.getBottom(), mPaint);
    }
}
