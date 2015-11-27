package com.jc.cash;

import android.content.Context;
import android.widget.ListView;

import com.jc.base.CommonAdapter;
import com.jc.base.Holder;
import com.jc.base.ResultData;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Created by lrh on 6/8/15.
 */
public class CashAdapter extends CommonAdapter<Cash> {


    public CashAdapter(Context context, Holder<Cash> holder, ListView listView){
        super(context,holder,listView);
    }

    protected List<Cash> transferData(ResultData data){
        Map<String,Object>  rs = (Map<String, Object>) data.getData();
        Object pros = rs.get("details");
        return gson.fromJson(gson.toJson(pros), new TypeToken<List<Cash>>() {
        }.getType());
    }

    @Override
    protected int getItemView() {

        return com.jc.R.layout.gift_item;
    }

}
