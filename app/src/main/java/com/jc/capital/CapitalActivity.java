package com.jc.capital;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.jc.details.DealDetailActivity;
import com.jc.ui.CommonTabFragmentActivity;

/**
 * Created by zy on 15/8/10.
 */
public class CapitalActivity extends CommonTabFragmentActivity implements ViewPager.OnPageChangeListener {


    private RelativeLayout dealDetailBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.capital_fund);
        dealDetailBar = (RelativeLayout) findViewById(com.jc.R.id.dealDetailBar);
        initView();
    }

    protected void initView(){
       super.initView();
        addRadioButton("持有中");
        addRadioButton("已兑付");

        Bundle unpayBundle = new Bundle();
        unpayBundle.putCharSequence("name", "待还款");
        CapitalFragment unpayFragment = new CapitalFragment();
        unpayFragment.setArguments(unpayBundle);
        addTabFragment(unpayFragment);

        Bundle payBundle = new Bundle();
        payBundle.putCharSequence("name","已还款");
        CapitalFragment payFragment = new CapitalFragment();
        payFragment.setArguments(payBundle);
        addTabFragment(payFragment);

        initViewPager();

    }

    @Override
    protected void onStart() {
        super.onStart();
        dealDetailBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CapitalActivity.this,DealDetailActivity.class));
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }



}
