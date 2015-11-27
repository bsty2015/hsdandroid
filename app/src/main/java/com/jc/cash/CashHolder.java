package com.jc.cash;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.base.Holder;
import com.jc.utils.DateFormatUtil;

import com.jc.utils.NumberUtils;

/**
 * Created by lrh on 23/7/15.
 */
public class CashHolder implements Holder<Cash> {

    private static String PAY_INFO ="%s获得,已于%s兑付";
    private TextView cashAmt;
    private TextView cashInfo;
    private LinearLayout cashBackgroup;
    private TextView source;




    public CashHolder(){

    }

    private CashHolder(View view){
        cashAmt = (TextView) view.findViewById(com.jc.R.id.cashAmt);
        cashInfo = (TextView) view.findViewById(com.jc.R.id.cashInfo);
        cashBackgroup = (LinearLayout) view.findViewById(com.jc.R.id.cashBackgroup);
        source = (TextView) view.findViewById(com.jc.R.id.source);
    }

    @Override
    public Holder build(View view) {
        return new CashHolder(view);
    }

    @Override
    public void setData(Cash obj) {
        cashAmt.setText(NumberUtils.formatNumber(obj.getAmt()));
        source.setText(obj.getSource());
        if(obj.getStatus() == 1){
            cashInfo.setText(DateFormatUtil.date2String(obj.getDate(), DateFormatUtil.DATE_FORMAT)+"获得");
        }else {
            cashBackgroup.setBackgroundResource(com.jc.R.mipmap.money2);
            cashInfo.setText(String.format(PAY_INFO,DateFormatUtil.date2String(obj.getDate(), DateFormatUtil.DATE_FORMAT),DateFormatUtil.date2String(obj.getRepayDate(), DateFormatUtil.DATE_FORMAT)));
        }

    }


}
