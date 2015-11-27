package com.jc.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jc.R;

/**
 * Created by zy on 15/8/14.
 */
public class CustomerActivity extends HeadMenuActiviyt {
     private TextView mcall;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_service);
        initView();
        setHeadTitleName("客户服务");


        mcall = (TextView) findViewById(R.id.call);

        mcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取电话号码
                String mobile = mcall.getText().toString();
                // 生成呼叫意图
                //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));   // 只进入拨号界面，不拨打

                // 开始呼叫
                startActivity(intent);
            }
        });
    }


}
