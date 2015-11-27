package com.jc.utils;

import android.app.Activity;

import com.jc.base.CustomApplication;

/**
 * Created by lrh on 14/9/15.
 */
public class ApplicationUtils {

    public static String getAccessToken(Activity context){
        return ((CustomApplication) context.getApplication()).accessToken;
    }

    public static Integer getUserId(Activity context){
        return ((CustomApplication) context.getApplication()).getUser().getId();
    }
}
