package com.jc.base;

import android.view.View;

/**
 * Created by lrh on 23/7/15.
 */
public interface Holder<T> {

    Holder build(View view);

    void setData(T obj);
}
