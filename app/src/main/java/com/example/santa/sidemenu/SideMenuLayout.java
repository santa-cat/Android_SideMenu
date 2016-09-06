package com.example.santa.sidemenu;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

/**
 * Created by santa on 16/7/11.
 */
public class SideMenuLayout extends ViewGroup {
    private final String DEBUG = "SideMenuLayout";
    private SideMenu mSideMenu;
    private View mContent;
    private float mDensity;
    //pull
    private boolean mIsCanbePulled = false;

    private int mLsatPosition = -1;
    private int mActivePointerId = -1;
    private long mLastTick = -1;
    private final int RELEASE_DURATION = 200;
    private final int CLICK_DURATION = 200;
    private Indicator mIndicator;

    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    public SideMenuLayout(Context context) {
        this(context, null);
    }

    public SideMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SideMenuLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mDensity = metrics.density;

        mSideMenu = new SideMenu(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mSideMenu.setLayoutParams(layoutParams);


        mIndicator = new Indicator(0, getPxFromDp(100), getPxFromDp(75));

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    private int getPxFromDp(int dp) {
        return (int) (mDensity*dp);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        //应该增加一个上拉加载
        if(count > 2) {
            Log.e("refresh", "SideMenuLayout only can host 2 elements");
            throw new IllegalStateException("SideMenuLayout only can host 2 elements");
        } else if (count <= 2){
            View child;
            for(int i = 0; i<count; i++) {
                child = getChildAt(i);
                if (!(child instanceof SideMenu)) {
                    mContent = child;
                }
            }
        }
        addView(mSideMenu);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(null != mSideMenu){
            measureChildWithMargins(mSideMenu, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }

        if (null != mContent) {
            measureChildWithMargins(mContent, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
    }

    public void setAdapter(ListAdapter adapter) {
        if(mSideMenu != null) {
            mSideMenu.setAdapter(adapter);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        if (mSideMenu != null && mSideMenu.getLeft()<=0) {
            int left = getMeasuredWidth() - mSideMenu.getRecfLenght();
            int top = 0;
            int right = left + mSideMenu.getMeasuredWidth();
            int bottom = mSideMenu.getMeasuredHeight();
            mSideMenu.layout(left, top, right, bottom);
        }

        if (null != mContent) {
            int left = 0;
            int top = 0;
            int right = mContent.getMeasuredWidth();
            int bottom = mContent.getMeasuredHeight();
            mContent.layout(left, top, right, bottom);
        }
    }

    private void obtainVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log("action = "+ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return isTouchInRect((int)ev.getX(), (int)ev.getY());
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;

        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLsatPosition = (int) event.getX();
                mLastTick = System.currentTimeMillis();
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                final int activePointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
                if (activePointerIndex == -1) {
                    Log.e("refresh", "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
                    break;
                }
                if(isCanBePulled()) {
                    int curPosition = (int) (event.getX());
                    moveBy(mLsatPosition - curPosition);
                    mLsatPosition = curPosition;
                }
                obtainVelocityTracker(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, mActivePointerId);
                if(Math.abs(initialVelocity) < mMinimumVelocity) {
                    initialVelocity = 0;
                }
                release(initialVelocity);

                break;

        }
        return true;
    }

    private void release(int velocity) {

        scrollToPosition(velocity);
        mLastTick = -1;
        setCanBePulled(false);

    }

    private boolean isAttachToNormalLeft(){
        if(mIndicator.getLeftOffset() <= (getWidth() - mSideMenu.getRecfLenght()-mSideMenu.getLeft())) {
            return true;
        } else {
            return false;
        }
    }

    private void scrollToPosition(int velocity) {
        boolean reslt = isAttachToNormalLeft();
        reslt = (velocity == 0) ? !reslt : !(velocity > 0);
        int targetLeft =  reslt ?  getWidth() - mSideMenu.getRecfLenght() - mIndicator.getLeftOffset(): getWidth() - mSideMenu.getRecfLenght();

        final ValueAnimator animator = ObjectAnimator.ofFloat(mSideMenu.getLeft() , targetLeft);
        animator.setDuration(RELEASE_DURATION);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float offset = (float)animator.getAnimatedValue();
                moveBy( mSideMenu.getLeft() - (int)offset);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                int state = isAttachToNormalLeft() ? SideMenu.ICON_FORK : SideMenu.ICON_ARROW;
                mSideMenu.setIcon(state);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animator.start();
    }


    private void moveBy(int offset) {

        if(null != mSideMenu) {
            if (!isAttachMaxMove(offset)) {
                mSideMenu.offsetLeftAndRight(-offset);
            }
        }
    }

    private boolean isAttachMaxMove(int offset) {
        if(mIndicator.getMaxLeftOffset()< (getWidth() - mSideMenu.getRecfLenght()-mSideMenu.getLeft() + offset)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isTouchInRect(int x, int y){
        RectF rectF = mSideMenu.getRecF();
        RectF rectFAct = new RectF(mSideMenu.getLeft(), rectF.top, getRight(), rectF.bottom);
//        Log("x = "+ x+" y = "+ y);
//        Log("mRectF.left = "+ rectFAct.left+" mRectF.right = "+ rectFAct.right);
        if(x>rectFAct.left && x<rectFAct.right && y>rectFAct.top && y<rectFAct.bottom) {
            setCanBePulled(true);
            return true;
        }
        return false;

    }

    public boolean isCanBePulled() {
        return mIsCanbePulled;
    }

    private void setCanBePulled(boolean b) {
        mIsCanbePulled = b;
    }

    private void Log(String s) {
        Log.d(DEBUG, s);
    }





    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        @SuppressWarnings({"unused"})
        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
