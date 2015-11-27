package com.jc.image;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by zy on 15/10/20.
 */
public interface LoadImage {

    void load(String url);

    void load(NetworkImageView imageView);
}
