package com.jc.ui;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by zy on 15/9/24.
 */
public class ViewPagerAdapter extends PagerAdapter{
    private List<View> viewList;

    public ViewPagerAdapter(List<View> viewList) {
        this.viewList =viewList;

    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        View item = viewList.get(position);
        ((ViewPager) container).addView(viewList.get(position));
        return item;
    }

}
