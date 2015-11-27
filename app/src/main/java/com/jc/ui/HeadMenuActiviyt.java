package com.jc.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import com.jc.base.CustomApplication;
import com.google.gson.Gson;
import com.jc.R;
import com.jc.utils.GsonRequest;

/**
 * Created by zy on 15/8/3.
 */
public  class HeadMenuActiviyt extends BaseActivity {
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
        mQueue = CustomApplication.mQueue;
        app = (CustomApplication) getApplication();
    }


    protected void initView(){
        if(backText == null){
            backText = (TextView) findViewById(R.id.backText);
        }
        if(backIcon == null ){
            backIcon = (ImageView) findViewById(R.id.backIcon);
        }
        mtitlebackground = (RelativeLayout) findViewById(R.id.coloback);


        findViewById(R.id.headBackBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setHeadTitleName(String name){
        TextView tv = (TextView)findViewById(R.id.titleText);
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
        mtitlebackground.setBackgroundResource(R.color.greyishBrown3);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mQueue.cancelAll(getActivityReqTag());
    }

    protected String getActivityReqTag(){
        return this.getClass().getCanonicalName();
    }

    protected void addRequest(GsonRequest request){
        request.setTag(getActivityReqTag());
        mQueue.add(request);
    }

    protected void showErrMsg(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
