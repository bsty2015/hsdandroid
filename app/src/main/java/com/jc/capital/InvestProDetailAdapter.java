package com.jc.capital;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.jc.base.CommonAdapter;
import com.jc.base.Holder;
import com.jc.base.ResultData;

import java.util.Map;

/**
 * Created by lrh on 6/8/15.
 */
public class InvestProDetailAdapter extends CommonAdapter<InvestProDetail> {

    public InvestProDetailAdapter(Context context, Holder<InvestProDetail> holder, ListView listView){
        super(context,holder,listView);
    }

    public void attach(ResultData data){
        Map<String,Object>  rs = (Map<String, Object>) data.getData();
        Object detail = rs.get("investProDetail");
        final InvestProDetail investProDetail = gson.fromJson(gson.toJson(detail), InvestProDetail.class);
        final Activity ac = (Activity) context;
        final InvestProDetailHolder holderTmp = (InvestProDetailHolder) holder;
        handler.post(new Runnable() {
            @Override
            public void run() {
                holderTmp.build(ac);
                holderTmp.setData(investProDetail);
            }
        });
    }

}
