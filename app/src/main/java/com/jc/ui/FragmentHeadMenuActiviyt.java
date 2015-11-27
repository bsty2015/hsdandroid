package com.jc.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jc.base.AddRequestQueueInterface;
import com.jc.base.CustomApplication;
import com.google.gson.Gson;

import com.jc.utils.GsonRequest;

/**
 * Created by zy on 15/8/3.
 */
public  class FragmentHeadMenuActiviyt extends BaseManagerActivity implements AddRequestQueueInterface {
    protected TextView titleText;

    protected RelativeLayout mtitlebackground;

    protected TextView backText;

    protected ImageView backIcon;

    protected RequestQueue mQueue;

    protected CustomApplication app;

    protected Gson gson = new Gson();

    protected GsonRequest request;

    protected Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CustomApplication) getApplication();
        mQueue = app.mQueue;
    }


    protected void initView(){
        if(backText == null){
            backText = (TextView) findViewById(com.jc.R.id.backText);
        }
        if(backIcon == null ){
            backIcon = (ImageView) findViewById(com.jc.R.id.backIcon);
        }
        mtitlebackground = (RelativeLayout) findViewById(com.jc.R.id.coloback);

        findViewById(com.jc.R.id.headBackBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setHeadTitleName(String name){
        TextView tv = (TextView)findViewById(com.jc.R.id.titleText);
        tv.setText(name);
    }

    protected void setBackText(String name){
        backText.setText(name);
    }
    protected void hideBackButton(){
        backIcon.setVisibility(View.INVISIBLE);

    }

    protected void hideBackText(){
        backText.setVisibility(View.INVISIBLE);
    }


    protected void settitlebackground(){
        mtitlebackground.setBackgroundResource(com.jc.R.color.darkSkyBlue);

    }

    @Override
    public void addRequest(Request request) {
        mQueue.add(request);
    }
}
