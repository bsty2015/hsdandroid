package com.jc.utils;

import com.google.gson.Gson;

/**
 * Created by lrh on 15/8/15.
 */
public class GsonUtil {

    private static Gson gson = new Gson();

    public static String toString(Object data){
        return  gson.toJson(data);
    }

    public static Object toObject(String data,Class type){
        return gson.fromJson(data,type);
    }
}
