package com.jc.utils;

import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by lrh on 9/9/15.
 */
public class ResourceUtils {

    public static int getMipmapResourceByReflect(String imageName){
        Class drawable  = com.jc.R.mipmap.class;
        Field field = null;
        int r_id ;
        try {
            field = drawable.getField(imageName);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            r_id=0;
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return r_id;
    }
}
