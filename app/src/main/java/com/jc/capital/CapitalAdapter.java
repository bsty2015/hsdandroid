package com.jc.capital;

import android.app.Activity;
import android.content.Context;

import android.widget.ListView;
import android.widget.TextView;

import com.jc.base.CommonAdapter;
import com.jc.base.Holder;
import com.jc.base.ResultData;
import com.google.gson.reflect.TypeToken;
import com.jc.R;
import static com.jc.utils.NumberUtils.formatNumber;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by lrh on 29/8/15.
 */
public class CapitalAdapter extends CommonAdapter<Capital> {


    private TextView totalCapital;

    public CapitalAdapter(Context context, Holder<Capital> holder, ListView listView) {
        super(context, holder, listView);
        totalCapital = (TextView) ((Activity)context).findViewById(R.id.totalCapital);
    }

    @Override
    protected List<Capital> transferData(ResultData data){
        Map<String,Object> rs = (Map<String, Object>) data.getData();
        sendTotalCapitalChange(String.valueOf(rs.get("totalAmt")));
        Object pros = rs.get("products");
        return gson.fromJson(gson.toJson(pros), new TypeToken<List<Capital>>() {
        }.getType());
    }

    @Override
    protected int getItemView() {

        return R.layout.capital_item;
    }

    private void sendTotalCapitalChange(final String amt){

        handler.post(new Runnable() {
            @Override
            public void run() {
                totalCapital.setText(formatNumber(new BigDecimal(amt)));
            }
        });
    }
}
