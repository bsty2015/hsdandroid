package com.jc.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 15/8/10.
 */
public class CommonTabFragmentActivity extends FragmentHeadMenuActiviyt{

    private CustomerViewPager viewPager;
    private RadioGroup rgChannel=null;
    private HorizontalScrollView hvChannel;

    private PageFragmentAdapter adapter=null;
    private List<Fragment> fragmentList=new ArrayList<Fragment>();

    private int buttonIndex = 0;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    protected void initView(){
        super.initView();
        rgChannel=(RadioGroup)super.findViewById(com.jc.R.id.rgChannel);
        viewPager=(CustomerViewPager)super.findViewById(com.jc.R.id.vpNewsList);
        rgChannel.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {
                        viewPager.setCurrentItem(checkedId);
                    }
                });

    }


    protected void addRadioButton(String name){
        RadioButton rb=(RadioButton) LayoutInflater.from(this).
                inflate(com.jc.R.layout.tab_rb, null);
        rb.setId(buttonIndex);
        rb.setText(name);
        RadioGroup.LayoutParams params=new
                RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        rgChannel.addView(rb, params);
        buttonIndex++;
    }

    protected void addTabFragment(Fragment fragment){
        fragmentList.add(fragment);
    }

    protected void initViewPager(){
        adapter=new PageFragmentAdapter(super.getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        rgChannel.check(0);
    }

    protected void check(int positoon){
        rgChannel.check(positoon);
    }


    public static class PageFragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragmentList;
        private FragmentManager fm;
        public PageFragmentAdapter(FragmentManager fm,List<Fragment> fragmentList){
            super(fm);
            this.fragmentList=fragmentList;
            this.fm=fm;
        }
        @Override
        public Fragment getItem(int idx) {

            return fragmentList.get(idx);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }
}
