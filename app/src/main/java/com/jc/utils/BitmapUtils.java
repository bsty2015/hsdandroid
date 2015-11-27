package com.jc.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * 图片处理类
 * 在android中，加载大背景图片或者大量图片时，经常导致内存溢出,该类主要是针对该问题的解决方法
 * Created by bsty on 11/11/15.
 */
public class BitmapUtils {

    /**
     * 将图片输入流转换成Bitmap格式图片
     * <p/>
     * 该方法在于直接调用JNI>>nativeDecodeAsset()来完成decode，
     * 无须再使用java层的createBitmap，从而节省了java层的空间
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap getBitmapFromSource(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 10;//width,hight设置为原来的十分之一
        InputStream inputStream = context.getResources().openRawResource(resId);
        Bitmap btp = BitmapFactory.decodeStream(inputStream);
        return btp;
    }

    /**
     * 以最省内存的方式读取本地资源图片
     * <p/>
     * 如果再读取时加上Config参数，可以更有效减少加载的内存，从而阻止out of Memory异常
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMapByMinMemory(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is);
    }


}
