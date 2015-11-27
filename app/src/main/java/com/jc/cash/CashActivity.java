package com.jc.cash;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jc.base.Configure;
import com.jc.R;
import com.jc.ui.CommonDisplayWindow;
import com.jc.ui.FreshenList;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.utils.GsonRequest;

/**
 * Created by zy on 15/8/10.
 */
public class CashActivity extends HeadMenuActiviyt {

    private GsonRequest request;

    protected FreshenList contentListview;

    private TextView cashRule;

    private String reqUrl = Configure.API_HOST+"/api/cash/list";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        cashRule = (TextView) findViewById(R.id.cashRule);
        initView();
        contentListview = (FreshenList)findViewById(R.id.listView);
        getData();
        setHeadTitleName("礼金");
        if(cashRule != null){
            cashRule.setVisibility(View.VISIBLE);
            cashRule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonDisplayWindow.show(CashActivity.this, R.layout.money_rules);
                }
            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        CashAdapter adapter = new CashAdapter(this,new CashHolder(),contentListview);
        request = new GsonRequest(reqUrl,adapter).withRequestParams("userId",app.getUserId().toString());
        addRequest(request);
    }

    public void getData() {


        // 调用MyListView中自定义接口
        contentListview.setonRefreshListener(new FreshenList.OnRefreshListener() {

            @Override
            public void onRefresh() {
                CashAdapter adapter = new CashAdapter(CashActivity.this,new CashHolder(),contentListview);
                request = new GsonRequest(reqUrl,adapter).withRequestParams("userId",app.getUserId().toString());
                addRequest(request);
                contentListview.onRefreshComplete();
            }
        });

    }
}
