package com.jc.capital;

import android.os.Bundle;

import com.jc.base.Configure;
import com.jc.ui.FreshenList;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.utils.GsonRequest;

/**
 * Created by zy on 15/8/10.
 */
public class UnpayCapitalActivity extends HeadMenuActiviyt {

    private GsonRequest request;

    protected FreshenList contentListview;

    private String reqUrl = Configure.API_HOST+"/api/user/invest/product";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.homepage);
        initView();
        contentListview = (FreshenList)findViewById(com.jc.R.id.listView);
        getData();
        setHeadTitleName("未支付订单");
    }

    @Override
    protected void onStart() {
        super.onStart();
        UnpayCapitalAdapter adapter = new UnpayCapitalAdapter(this,new UnpayCapitalHolder(this),contentListview);
        adapter.withHandler(handler);
        request = new GsonRequest(reqUrl,adapter);
        request.withRequestParams("userId", app.getUserId().toString()).withRequestParams("status", "支付中");
        addRequest(request);
    }


    public void getData() {


        // 调用MyListView中自定义接口
        contentListview.setonRefreshListener(new FreshenList.OnRefreshListener() {

            @Override
            public void onRefresh() {
                UnpayCapitalAdapter adapter = new UnpayCapitalAdapter(UnpayCapitalActivity.this,new UnpayCapitalHolder(),contentListview);
                adapter.withHandler(handler);
                request = new GsonRequest(reqUrl,adapter);
                request.withRequestParams("userId", app.getUserId().toString()).withRequestParams("status", "支付中");
                addRequest(request);
                contentListview.onRefreshComplete();
            }
        });

    }

}
