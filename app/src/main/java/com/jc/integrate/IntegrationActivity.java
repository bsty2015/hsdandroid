package com.jc.integrate;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jc.R;
import com.jc.ui.CommonDisplayWindow;
import com.jc.ui.CommonTabFragmentActivity;

/**
 * Created by zy on 15/9/2.
 */
public class IntegrationActivity  extends CommonTabFragmentActivity implements ViewPager.OnPageChangeListener {


    private TextView mtotalCredit;

    private TextView interestRule;

    private  TextView tlit;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integrate_layout);

        initView();
        setHeadTitleName("积分");

        mtotalCredit = (TextView) findViewById(R.id.totalCredit);
        interestRule = (TextView) findViewById(R.id.interestRule);

        interestRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDisplayWindow.show(IntegrationActivity.this,R.layout.integrate_rule_layout);
            }
        });

    }

    protected void initView(){
        super.initView();
        addRadioButton("获得的积分");
        addRadioButton("已兑换");

        Bundle unpayBundle = new Bundle();
        unpayBundle.putCharSequence("name", "false");
        IntegrationFragment unpayFragment = new IntegrationFragment();
        unpayFragment.setArguments(unpayBundle);
        addTabFragment(unpayFragment);

        Bundle payBundle = new Bundle();
        payBundle.putCharSequence("name", "true");
        IntegrationFragment payFragment = new IntegrationFragment();
        payFragment.setArguments(payBundle);
        addTabFragment(payFragment);

        initViewPager();

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
