package com.jc.message;

import android.content.Intent;
import android.os.Bundle;

import com.jc.base.Configure;
import com.jc.R;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.utils.GsonRequest;

/**
 * Created by zy on 15/8/10.
 */
public class MessageDetailActivity extends HeadMenuActiviyt {

    private Integer msgId;

    private String reqUrl = Configure.API_HOST+"/api/message/detail";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_detail_layout);
        initView();
        setHeadTitleName("消息");
        Intent intent = getIntent();
        msgId = intent.getIntExtra("id",0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MessageDetailAdapter adapter = new MessageDetailAdapter(this,new MessageDetailHolder(),null);
        request = new GsonRequest(reqUrl,adapter)
                .withRequestParams("userId", app.getUserId().toString())
                .withRequestParams("msgId", msgId.toString());
        addRequest(request);
    }
}
