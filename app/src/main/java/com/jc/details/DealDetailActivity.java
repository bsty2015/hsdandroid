package com.jc.details;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jc.ui.CommonTabFragmentActivity;

/**
 * Created by zy on 15/8/10.
 */
public class DealDetailActivity extends CommonTabFragmentActivity implements ViewPager.OnPageChangeListener {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.deal_detail_main);
        setHeadTitleName("交易明细");
        initView();
    }

    protected void initView(){
       super.initView();
        addRadioButton("全部");
        addRadioButton("本金");
        addRadioButton("收益");
        addRadioButton("礼金");
        addRadioButton("兑付");

        Bundle allBundle = new Bundle();
        allBundle.putCharSequence("type", "all");
        DealDetailFragment allFragment = new DealDetailFragment();
        allFragment.setArguments(allBundle);
        addTabFragment(allFragment);

        Bundle corpusBundle = new Bundle();
        corpusBundle.putCharSequence("type", "corpus");
        DealDetailFragment corpusFragment = new DealDetailFragment();
        corpusFragment.setArguments(corpusBundle);
        addTabFragment(corpusFragment);

        Bundle profitBundle = new Bundle();
        profitBundle.putCharSequence("type", "profit");
        DealDetailFragment profitFragment = new DealDetailFragment();
        profitFragment.setArguments(profitBundle);
        addTabFragment(profitFragment);

        Bundle giftBundle = new Bundle();
        giftBundle.putCharSequence("type", "gift");
        DealDetailFragment giftFragment = new DealDetailFragment();
        giftFragment.setArguments(giftBundle);
        addTabFragment(giftFragment);

        Bundle cashBundle = new Bundle();
        cashBundle.putCharSequence("type", "cash");
        DealDetailFragment cashFragment = new DealDetailFragment();
        cashFragment.setArguments(cashBundle);
        addTabFragment(cashFragment);

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
