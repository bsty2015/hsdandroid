package com.jc.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jc.base.ActivityManagerApplication;

/**
 * Created by lrh on 25/9/15.
 */
public class BaseManagerActivity extends FragmentActivity {

    protected ActivityManagerApplication activityManagerApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManagerApplication = (ActivityManagerApplication) getApplication();
        if (activityManagerApplication != null) {
            activityManagerApplication.addActivity(this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activityManagerApplication != null) {
            activityManagerApplication.finishActivity(this);
        }
    }
}
