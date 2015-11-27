package com.jc.product;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.jc.base.CommonAdapter;
import com.jc.base.Holder;
import com.jc.base.ResultData;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by lrh on 6/8/15.
 */
public class ProductDetailAdapter extends CommonAdapter<ProductDetail> {

    private  Gson gson;


    public ProductDetailAdapter(Context context, Holder<ProductDetail> holder, ListView listView){
        super(context,holder,listView);
        gson =new Gson();
    }


    public void attach(ResultData data){
        Map<String,Object>  rs = (Map<String, Object>) data.getData();
        Object pro = rs.get("product");
        final ProductDetail proDetail = gson.fromJson(gson.toJson(pro), ProductDetail.class);
        final Activity ac = (Activity) context;
        handler.post(new Runnable() {
            @Override
            public void run() {
                ((ProductDetailHolder)holder).build(ac);
                ((ProductDetailHolder) holder).setData(proDetail);
            }
        });
    }

}








