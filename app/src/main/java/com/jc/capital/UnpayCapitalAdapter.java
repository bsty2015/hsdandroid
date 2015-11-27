package com.jc.capital;

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
 * Created by lrh on 29/8/15.
 */
public class UnpayCapitalAdapter extends CommonAdapter<Capital> {


    public UnpayCapitalAdapter(Context context, Holder<Capital> holder, ListView listView) {
        super(context, holder, listView);
    }



    @Override
    protected List<Capital> transferData(ResultData data){
        Map<String,Object> rs = (Map<String, Object>) data.getData();
        Object pros = rs.get("products");
        return gson.fromJson(gson.toJson(pros), new TypeToken<List<Capital>>() {
        }.getType());
    }

    @Override
    protected int getItemView() {

        return R.layout.unpay_capital_item;
    }
}
