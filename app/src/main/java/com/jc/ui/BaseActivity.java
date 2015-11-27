package com.jc.ui;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.jc.base.ActivityManagerApplication;

/**
 * Created by lrh on 25/9/15.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ((ActivityManagerApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ((ActivityManagerApplication)getApplication()).finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
