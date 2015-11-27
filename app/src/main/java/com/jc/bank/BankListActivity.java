package com.jc.bank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jc.base.Configure;
import com.jc.R;
import com.jc.ui.FreshenList;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.utils.GsonRequest;

/**
 * Created by zy on 15/8/10.
 */
public class BankListActivity extends HeadMenuActiviyt {

    private GsonRequest request;

    protected FreshenList contentListview;

    private TextView addCardButton;

    private String reqUrl = Configure.API_HOST+"/api/card/list";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbank);
        initView();
        contentListview = (FreshenList)findViewById(R.id.listView);
        getData();
        setHeadTitleName("我的绑卡");
        addCardButton = (TextView) findViewById(R.id.addCardButton);
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BankListActivity.this,AddCardActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        BankListAdapter adapter = new BankListAdapter(this,new BankListHolder(),contentListview);
        request = new GsonRequest(reqUrl,adapter).withRequestParams("userId",app.getUserId().toString());
        addRequest(request);
    }


    public void getData() {


        // 调用MyListView中自定义接口
        contentListview.setonRefreshListener(new FreshenList.OnRefreshListener() {

            @Override
            public void onRefresh() {
                BankListAdapter adapter = new BankListAdapter(BankListActivity.this,new BankListHolder(),contentListview);
                request = new GsonRequest(reqUrl,adapter).withRequestParams("userId",app.getUserId().toString());
                addRequest(request);
                contentListview.onRefreshComplete();
            }
        });

    }

}
