package com.jc.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.jc.R;
import com.jc.index.IndexFragment;
import com.jc.product.ProductFragment;
import com.jc.user.UserFragment;
import com.jc.utils.StringUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by lrh on 9/8/15.
 */
public class MainTabActivity extends BaseManagerActivity implements TabHost.OnTabChangeListener {

    //定义FragmentTabHost对象
    private FragmentTabHost mTabHost;

    //定义一个布局
    private LayoutInflater layoutInflater;


    //定义数组来存放Fragment界面
    private Class fragmentArray[] = {IndexFragment.class, ProductFragment.class, UserFragment.class, MoreFragment.class};

    //定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.tab_index_btn, R.drawable.tab_product_btn, R.drawable.tab_user_btn, R.drawable.tab_more_btn};

    //Tab选项卡的文字
    private String mTextviewArray[] = {"首页", "理财产品", "个人中心", "更多"};

    private String selectedIndex = null;

    private int clickNum = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);
        Intent intent = getIntent();
        selectedIndex = intent.getStringExtra("mainTab");
        //初始化个推服务
//        PushManager.getInstance().initialize(this.getApplicationContext());
        initView();
        if (!StringUtils.isEmpty(selectedIndex)) {
            mTabHost.onTabChanged(selectedIndex);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(0);//状态栏无背景
//        tintManager.setStatusBarTintResource(R.color.hongse);//通知栏所需颜色

        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        findViewById(R.id.mainLayout).setPadding(0, config.getPixelInsetTop(false), 0, config.getPixelInsetBottom());

    }

    @Override
    protected void onStart() {
        super.onStart();
        clickNum = 0;
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 初始化组件
     */
    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        //得到fragment的个数 
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));

            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            //mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }


        TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(R.id.textview);
        tv.setTextColor(this.getResources().getColor(R.color.has_been_honored));
        mTabHost.setOnTabChangedListener(this);
        mTabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        clickNum = 0;
        mTabHost.setCurrentTabByTag(tabId);
        updateTab(mTabHost);
    }

    /**
     * 更新Tab标签的颜色，和字体的颜色
     *
     * @param tabHost
     */
    private void updateTab(final TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(R.id.textview);
            if (tabHost.getCurrentTab() == i) {//选中
                tv.setTextColor(this.getResources().getColor(R.color.has_been_honored));
            } else {//不选中
                tv.setTextColor(this.getResources().getColor(R.color.warmGrey3));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickNum++;
            showErrMsg("再按一次将退出应用");
            if (clickNum == 2) {
                activityManagerApplication.AppExit();
            }
            return true;
        }
        return false;

    }

    private void showErrMsg(String msg) {
        Toast toast = Toast.makeText(MainTabActivity.this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }


}
