package com.example.santa.sidemenu;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by santa on 16/7/12.
 */
public class RecyclePlayView  extends ViewGroup{
    private LinkedList<HashMap<String, Object>> mViewList;
    private final static String VIEW = "view";
    private final static String FLAG = "flag";
    private final static int LAYOUT_FIN = 1;
    private final static int LAYOUT_NO = 2;
    private float mDensity;
    LinearLayout.LayoutParams layoutParams;

    public RecyclePlayView(Context context) {
        this(context, null);
    }

    public RecyclePlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclePlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RecyclePlayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mDensity = metrics.density;

        mViewList = new LinkedList<>();
        layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for(int i = 0 ; i<count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int count = getChildCount();
        for(int j = 0 ; j<count && j<mViewList.size(); j++) {
            if((int)mViewList.get(j).get(FLAG) == LAYOUT_NO) {
                View v = (View) mViewList.get(j).get(VIEW);
                int left = getWidth();
                int top = (int) ((Math.random()*(getMeasuredHeight() - v.getMeasuredHeight())));
                int right = left + v.getMeasuredWidth();
                int bottom = top + v.getMeasuredHeight();
                v.layout(left, top, right, bottom);
                startAnimotor(v);
                mViewList.get(j).put(FLAG, LAYOUT_FIN);
            }
        }

    }

    private void startAnimotor(View v) {
        ValueAnimator anim = ObjectAnimator.ofFloat(v, "translationX", 0, -(getMeasuredWidth() + v.getMeasuredWidth()));
        anim.setDuration(10000);
        anim.setInterpolator(new LinearInterpolator());
        anim.addListener(new AnimtorListener());

        anim.start();
    }



    public void addStrip(ArrayList<View> list) {
        for (int i = 0 ; i<list.size(); i++) {
            addStrip(list.get(i));
        }
    }

    public void addStrip(View v) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(VIEW, v);
        map.put(FLAG, LAYOUT_NO);
        mViewList.add(map);
        addView(v, layoutParams);
    }

    private void removeStrip(View v) {
        for (int i = 0 ; i<mViewList.size(); i++) {
            if(mViewList.get(i).get(VIEW) == v) {
                mViewList.remove(i);
            }
        }
//        Log.d("DEBUG_SANTA", "size = "+mViewList.size());
        removeView(v);

    }

    private class AnimtorListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            removeStrip((View) ((ObjectAnimator)animator).getTarget());
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }


}
