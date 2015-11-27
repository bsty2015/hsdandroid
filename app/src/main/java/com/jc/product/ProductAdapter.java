package com.jc.product;

import android.content.Context;
import android.widget.ListView;

import com.jc.base.CommonAdapter;
import com.jc.base.Holder;
import com.jc.base.ResultData;

import com.google.gson.reflect.TypeToken;
import com.jc.R;

import java.util.List;
import java.util.Map;

/**
 * Created by lrh on 6/8/15.
 */
public class ProductAdapter extends CommonAdapter<Product> {


    public ProductAdapter(Context context,Holder<Product> holder,ListView listView){
        super(context,holder,listView);
    }

    protected List<Product> transferData(ResultData data){
        Map<String,Object>  rs = (Map<String, Object>) data.getData();
        Object pros = rs.get("products");
        return gson.fromJson(gson.toJson(pros), new TypeToken<List<Product>>() {
        }.getType());
    }

    @Override
    protected int getItemView() {

        return R.layout.produce_item;
    }

}
