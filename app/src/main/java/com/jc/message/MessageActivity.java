package com.jc.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.jc.base.Configure;
import com.jc.ui.FreshenList;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.utils.GsonRequest;

/**
 * Created by zy on 15/8/10.
 */
public class MessageActivity extends HeadMenuActiviyt {

    private GsonRequest request;

    protected FreshenList contentListview;

    private String reqUrl = Configure.API_HOST+"/api/message/list";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.homepage);
        initView();
        setHeadTitleName("消息");
        contentListview = (FreshenList)findViewById(com.jc.R.id.listView);
        getData();
        contentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = (Message) parent.getItemAtPosition(position);
                Intent intent = new Intent(MessageActivity.this, MessageDetailActivity.class);
                intent.putExtra("id", message.getMsgId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        MessageAdapter adapter = new MessageAdapter(this,new MessageHolder(),contentListview);
        request = new GsonRequest(reqUrl,adapter).withRequestParams("userId",app.getUserId().toString());
        addRequest(request);
    }


    public void getData() {

        // 调用MyListView中自定义接口
        contentListview.setonRefreshListener(new FreshenList.OnRefreshListener() {

            @Override
            public void onRefresh() {
                MessageAdapter adapter = new MessageAdapter(MessageActivity.this,new MessageHolder(),contentListview);
                request = new GsonRequest(reqUrl,adapter).withRequestParams("userId",app.getUserId().toString());
                addRequest(request);
                contentListview.onRefreshComplete();
            }
        });

    }
}
