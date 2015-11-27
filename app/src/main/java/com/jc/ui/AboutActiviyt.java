package com.jc.ui;

import android.os.Bundle;

/**
 * Created by zy on 15/8/14.
 */
public class AboutActiviyt extends HeadMenuActiviyt {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.about_collection);
        initView();
        setHeadTitleName("关于集财");

    }

}