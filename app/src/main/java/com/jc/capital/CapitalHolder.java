package com.jc.capital;

import android.view.View;
import android.widget.TextView;

import com.jc.base.Holder;
import com.jc.utils.DateFormatUtil;

import static com.jc.utils.NumberUtils.formatNumber;

/**
 * Created by lrh on 29/8/15.
 */
public class CapitalHolder implements Holder<Capital> {

    private TextView capitalOrderId;

    private TextView capitalDateInfo;

    private TextView capitalName;

    private TextView capitalCorpus;

    private TextView capitalProfit;

    private String status;

    public  CapitalHolder(String status){
        this.status = status;
    }

    private CapitalHolder(View view,String status){
        capitalOrderId = (TextView) view.findViewById(com.jc.R.id.capitalOrderId);
        capitalDateInfo = (TextView) view.findViewById(com.jc.R.id.capitalDateInfo);
        capitalName = (TextView) view.findViewById(com.jc.R.id.capitalName);
        capitalCorpus = (TextView) view.findViewById(com.jc.R.id.capitalCorpus);
        capitalProfit = (TextView) view.findViewById(com.jc.R.id.capitalProfit);
        this.status = status;

    }

    @Override
    public Holder build(View view) {
        return new CapitalHolder(view,status);
    }

    @Override
    public void setData(Capital obj) {
        capitalOrderId.setText(obj.getOrderId());
        capitalName.setText(obj.getProductName());
        capitalCorpus.setText(formatNumber(obj.getCorpus()));
        capitalProfit.setText(formatNumber(obj.getProfit()));
        if("待还款".equals(status)){
            capitalDateInfo.setText("将于"+ DateFormatUtil.date2String(obj.getExpireDate(),DateFormatUtil.DATE_FORMAT)+"兑付");
        }else{
            capitalDateInfo.setText("已于"+ DateFormatUtil.date2String(obj.getExpireDate(),DateFormatUtil.DATE_FORMAT)+"兑付");
        }
    }
}
