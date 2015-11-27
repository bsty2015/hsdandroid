package com.jc.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.baidu.mobstat.StatService;
import com.jc.R;
import com.squareup.leakcanary.RefWatcher;
import com.jc.utils.GsonRequest;

/**
 * Created by lrh on 18/9/15.
 */
public abstract class CommonFragment extends Fragment {

    protected CustomApplication app;

    private RequestQueue mQueue;

    protected View rootView;

    protected boolean isVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CustomApplication) getActivity().getApplication();
        mQueue = CustomApplication.mQueue;

    }

    protected void setHeadTitleName(String name) {
        TextView tv = (TextView) rootView.findViewById(R.id.titleText);
        tv.setText(name);

    }

    protected void hideBackButton() {
        rootView.findViewById(R.id.backIcon).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.backText).setVisibility(View.INVISIBLE);
    }


    protected String getFragmentReqTag() {
        return this.getClass().getCanonicalName();
    }

    protected void addRequest(GsonRequest request) {
        request.setTag(getFragmentReqTag());
        mQueue.add(request);
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPause(this);
        mQueue.cancelAll(getFragmentReqTag());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = CustomApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    /**
     * 重写该方法实现fragment懒加载
     * 即可实现在fragment可见时才进行数据加载操作
     * 正常情况下
     * 由于Viewpager的缓存特点，Viewpager启动时其第一个Fragment页面及待缓存的页面都将按顺序呢开始他们的正常生命周期，
     * 所以如果不用懒加载，会很占用内存
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (getUserVisibleHint()) {
//            isVisible = true;
//            onVisible();
//        } else {
//            isVisible = false;
//            onInvisible();
//        }

    }

    
    protected void onInvisible() {
    }

    protected abstract void lazyLoad();

    protected void onVisible() {
        lazyLoad();
    }


}
