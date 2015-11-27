package com.jc.index;

import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.jc.base.CustomApplication;
import com.jc.base.Holder;
import com.jc.image.CommonLoadImage;

/**
 * Created by lrh on 23/7/15.
 */
public class IndexHolder implements Holder<IndexModule> {

    NetworkImageView indexItem;


    public IndexHolder(){

    }

    private IndexHolder(View view){
        indexItem = (NetworkImageView) view.findViewById(com.jc.R.id.indexItem);

    }

    @Override
    public Holder build(View view) {
        return new IndexHolder(view);
    }

    @Override
    public void setData(IndexModule obj) {
        CommonLoadImage.getInstance(CustomApplication.mQueue).load(indexItem,obj.getImageName());
    }

}
