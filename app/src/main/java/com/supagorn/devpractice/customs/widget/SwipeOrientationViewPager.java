package com.supagorn.devpractice.customs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.supagorn.devpractice.R;
import com.supagorn.devpractice.customs.DepthPageTransformer;

public class SwipeOrientationViewPager extends ViewPager {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int mSwipeOrientation;

    public SwipeOrientationViewPager(Context context) {
        super(context);
        mSwipeOrientation = HORIZONTAL;
    }

    public SwipeOrientationViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSwipeOrientation(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(mSwipeOrientation == VERTICAL ? swapXY(event) : event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        if (mSwipeOrientation == VERTICAL) {
//            boolean intercepted = super.onInterceptHoverEvent(swapXY(event));
//            swapXY(event);
//            return intercepted;
//        }
        return super.onInterceptTouchEvent(event);
    }

    public void setSwipeOrientation(int swipeOrientation) {
        if (swipeOrientation == HORIZONTAL || swipeOrientation == VERTICAL)
            mSwipeOrientation = swipeOrientation;
        else
            throw new IllegalStateException("Swipe Orientation can be either CustomViewPager.HORIZONTAL" +
                    " or CustomViewPager.VERTICAL");
        initSwipeMethods();
    }

    private void setSwipeOrientation(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeOrientationViewPager);
        mSwipeOrientation = typedArray.getInteger(R.styleable.SwipeOrientationViewPager_swipe_orientation, 0);
        typedArray.recycle();
        initSwipeMethods();
    }

    private void initSwipeMethods() {
        if (mSwipeOrientation == VERTICAL) {
            // The majority of the work is done over here
            setPageTransformer(true, new DepthPageTransformer());
            // The easiest way to get rid of the overscroll drawing that happens on the left and right
            setOverScrollMode(OVER_SCROLL_ALWAYS);
        }
    }

    private MotionEvent swapXY(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float newX = (event.getY() / height) * width;
        float newY = (event.getX() / width) * height;

        event.setLocation(newX, newY);
        return event;
    }


}