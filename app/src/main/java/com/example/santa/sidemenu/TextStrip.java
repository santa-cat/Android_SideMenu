package com.example.santa.sidemenu;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by santa on 16/7/12.
 */
public class TextStrip extends LinearLayout{
    private TextView textView;
    private LadderView ladderView;
    private float mDensity;
    private int textBackgroundColor = 0xffF8DB01;
    private int textColor = Color.WHITE;



    public TextStrip(Context context) {
        this(context, null);
    }

    public TextStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TextStrip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mDensity = metrics.density;

        ladderView = new LadderView(context);
        LayoutParams layoutParams = new LayoutParams(getPxFromDp(25), getPxFromDp(25));
        layoutParams.gravity = Gravity.CENTER;
        ladderView.setLayoutParams(layoutParams);
        addView(ladderView);

        textView = new TextView(context);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(16);
        textView.setTextColor(textColor);
        textView.setBackgroundColor(textBackgroundColor);
        addView(textView);

    }

    private int getPxFromDp(int dp) {
        return (int) (mDensity*dp);
    }


    public void setText(String text) {
        if (null != textView) {
            textView.setText(text);
        }
    }



}
