package com.jc.capital;

import android.content.Intent;
import android.os.Bundle;

import com.jc.base.Configure;
import com.jc.R;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.utils.GsonRequest;
import com.jc.utils.StringUtils;

/**
 * Created by lrh on 6/8/15.
 */
public class InvestProDetailActivity extends HeadMenuActiviyt {

    private String reqUrl = Configure.API_HOST+"/api/user/invest/productDetail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asset_details);
        initView();
        setHeadTitleName("订单详情");
        Intent intent =getIntent();
        String id = intent.getStringExtra("id");
        InvestProDetailAdapter adapter = new InvestProDetailAdapter(this,new InvestProDetailHolder(),null);
        if(!StringUtils.isEmpty(id)){
            request = new GsonRequest(reqUrl,adapter);
            request.withRequestParams("id", id);
            addRequest(request);
        }
    }
}
