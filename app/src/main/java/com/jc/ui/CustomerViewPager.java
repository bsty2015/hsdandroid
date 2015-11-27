package com.jc.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lrh on 16/9/15.
 */
public class CustomerViewPager extends ViewPager {

    private boolean disableScroll=true;

    public CustomerViewPager(Context context) {
        super(context);
    }

    public CustomerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // LogHelper.i(TAG, "MyViewPager dispatchTouchEvent, ");
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(disableScroll){
            return false;
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (disableScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    public boolean isDisableScroll() {
        return disableScroll;
    }

    public void setDisableScroll(boolean disableScroll) {
        this.disableScroll = disableScroll;
    }

}
