package com.jc.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.jc.base.CustomApplication;
import com.jc.R;
import com.jc.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zy on 15/9/24.
 */
public class WelcomActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;

    private List<View> viewList;

    private String stop;

    private Bitmap btm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomApplication app = (CustomApplication) getApplication();
        if (!app.isFirstStartUp()) {
            startActivity(new Intent(WelcomActivity.this, MainTabActivity.class));
            return;
        }
        // 设置无标题窗口
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        viewPager = (ViewPager) findViewById(R.id.vpage);

        LayoutInflater inflater = LayoutInflater.from(this);
        viewList = new ArrayList<>();
        View item1 = inflater.inflate(R.layout.welcome2, null);
        //直接读取本地资源图片会影响内存，很容易out of memory
        btm = BitmapUtils.getBitmapFromSource(this, R.mipmap.startover);

        item1.findViewById(R.id.startover).setBackgroundDrawable(new BitmapDrawable(btm));
//        item1.findViewById(R.id.startover).setBackgroundResource(R.mipmap.startover);
        viewList.add(item1);


        View item2 = inflater.inflate(R.layout.welcome2, null);
        btm = BitmapUtils.getBitmapFromSource(this, R.mipmap.startover1);
        item2.findViewById(R.id.startover).setBackgroundDrawable(new BitmapDrawable(btm));
//        item2.findViewById(R.id.startover).setBackgroundResource(R.mipmap.startover1);
        viewList.add(item2);


        View item3 = inflater.inflate(R.layout.welcome2, null);
        btm = BitmapUtils.getBitmapFromSource(this, R.mipmap.startover2);
        item3.findViewById(R.id.startover).setBackgroundDrawable(new BitmapDrawable(btm));
//        item3.findViewById(R.id.startover).setBackgroundResource(R.mipmap.startover2);
        viewList.add(item3);


        View item4 = inflater.inflate(R.layout.welcome2, null);
        btm = BitmapUtils.getBitmapFromSource(this, R.mipmap.startover3);
        LinearLayout bg = (LinearLayout) item4.findViewById(R.id.startover);
        bg.setBackgroundDrawable(new BitmapDrawable(btm));
//        bg.setBackgroundResource(R.mipmap.startover3);
        viewList.add(item4);
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomActivity.this, MainTabActivity.class));
                WelcomActivity.this.finish();
            }
        });

        viewPager.setAdapter(new ViewPagerAdapter(viewList));
        viewPager.setOnPageChangeListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (btm != null) {
            btm.recycle();
            btm = null;
        }
        System.gc();
    }
}
