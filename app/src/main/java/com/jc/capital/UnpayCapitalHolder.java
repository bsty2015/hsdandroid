package com.jc.capital;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.base.Holder;
import com.jc.pay.PayActivity;

import com.jc.utils.NumberUtils;

/**
 * Created by lrh on 29/8/15.
 */
public class UnpayCapitalHolder implements Holder<Capital> {

    private TextView unpayCapitalOrderId;

    private TextView unpayCapitalName;

    private TextView expireInfo;

    private TextView unpayCapitalCorpus;

    private TextView unpayCapitalProfit;

    private LinearLayout repeatPayButton;

    private  Activity context;

    public UnpayCapitalHolder(Activity context){
        this.context = context;
    }

    public UnpayCapitalHolder(){

    }

    private UnpayCapitalHolder(View view,final Activity context){
        unpayCapitalOrderId = (TextView) view.findViewById(com.jc.R.id.unpayCapitalOrderId);
        unpayCapitalName = (TextView) view.findViewById(com.jc.R.id.unpayCapitalName);
        expireInfo = (TextView) view.findViewById(com.jc.R.id.expireInfo);
        unpayCapitalCorpus = (TextView) view.findViewById(com.jc.R.id.unpayCapitalCorpus);
        unpayCapitalProfit = (TextView) view.findViewById(com.jc.R.id.unpayCapitalProfit);
        repeatPayButton = (LinearLayout) view.findViewById(com.jc.R.id.repeatPayButton);
        repeatPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PayActivity.class);
                intent.putExtra("orderId",unpayCapitalOrderId.getText());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public Holder build(View view) {
        return new UnpayCapitalHolder(view,context);
    }

    @Override
    public void setData(Capital obj) {
        unpayCapitalName.setText(obj.getProductName());
        unpayCapitalOrderId.setText(obj.getOrderId());
        unpayCapitalCorpus.setText(NumberUtils.formatNumber(obj.getCorpus()));
        unpayCapitalProfit.setText(NumberUtils.formatNumber(obj.getProfit()));

    }
}
