package com.jc.integrate;

import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

import com.jc.base.CommonAdapter;
import com.jc.base.Holder;
import com.jc.base.ResultData;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by zy on 15/9/2.
 */
public class IntegratioinAdapter extends CommonAdapter<integral> {

    private TextView totalCredit;

    public IntegratioinAdapter(Activity activity, Holder<integral> holder, ListView listView) {
        super(activity, holder, listView);
        totalCredit = (TextView) activity.findViewById(com.jc.R.id.totalCredit);
    }


    @Override
    protected List<integral> transferData(ResultData data){
        final Map<String,Object> rs = (Map<String, Object>) data.getData();
        Object pros = rs.get("credits");
        if(pros == null){
            return  Collections.EMPTY_LIST;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                totalCredit.setText(String.valueOf(Double.valueOf(rs.get("totalCredit").toString()).intValue()));
            }
        });

        return gson.fromJson(gson.toJson(pros), new TypeToken<List<integral>>() {
        }.getType());
    }

    @Override
    protected int getItemView() {

        return com.jc.R.layout.integral_item;

    }

}
