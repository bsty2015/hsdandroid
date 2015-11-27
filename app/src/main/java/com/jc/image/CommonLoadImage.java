package com.jc.image;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by lrh on 23/10/15.
 */
public class CommonLoadImage {


    private static  CommonLoadImage instance;

    private static BitmapCache imageCache = new BitmapCache();

    private ImageLoader imageLoader;

    public static CommonLoadImage getInstance(RequestQueue mQueue){
        if(instance != null){
            return instance;
        }else{
            synchronized (CommonLoadImage.class){
                if(instance == null){
                    instance = new CommonLoadImage(mQueue);
                }
            }
        }
        return instance;

    }

    private CommonLoadImage(RequestQueue mQueue){
        imageLoader = new ImageLoader(mQueue, imageCache);
    }

    public void load(NetworkImageView imageView,String url){
        imageView.setImageUrl(url, imageLoader);
    }
}
