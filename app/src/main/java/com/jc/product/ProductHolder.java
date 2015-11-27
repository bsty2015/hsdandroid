package com.jc.product;

import android.view.View;
import android.widget.TextView;

import com.jc.base.Holder;

import static com.jc.utils.NumberUtils.formatNumber;
import static com.jc.utils.NumberUtils.formatNormalNumber;

import java.math.BigDecimal;

/**
 * Created by lrh on 23/7/15.
 */
public class ProductHolder implements Holder<Product> {

    TextView name;
    TextView interestRate;
    TextView collectDuration;
    TextView remainAmt;
    TextView statusView;
    TextView extraRate;


    public ProductHolder(){

    }

    private  ProductHolder(View view){
        name = (TextView) view.findViewById(com.jc.R.id.name);
        interestRate = (TextView) view.findViewById(com.jc.R.id.interestRate);
        collectDuration = (TextView) view.findViewById(com.jc.R.id.collectDuration);
        remainAmt = (TextView) view.findViewById(com.jc.R.id.remainAmt);
        statusView = (TextView) view.findViewById(com.jc.R.id.pro_status);
        extraRate = (TextView) view.findViewById(com.jc.R.id.extralnterestRate);

    }

    @Override
    public Holder build(View view) {
        return new ProductHolder(view);
    }

    @Override
    public void setData(Product obj) {
        String status = obj.getStatus();
        if("兑付中".equals(status)){
            status = "售完";
        }
        statusView.setText(status);
        statusView.setBackgroundResource(getStatusColor(status));

        name.setText(obj.getName());
        collectDuration.setText(obj.getDuration().toString());
        remainAmt.setText(formatNumber(obj.getRemainAmt()));
        interestRate.setText(formatNumber(obj.getRate())+"%");
        BigDecimal eRate = obj.getExtraRate();
        if(eRate != null ){
            extraRate.setText("+"+formatNormalNumber(eRate)+"%");
        }

    }

    private int getStatusColor(String status){
        Boolean isEq = "销售中".equals(status);
        if ("销售中".equals(status)) {
            return com.jc.R.drawable.grapefruit_background;
        }else if ("待上架".equals(status)) {
            return com.jc.R.drawable.presell_background;
        }else if ("售完".equals(status) || "兑付中".equals(status)) {
            return com.jc.R.drawable.sell_background;
        }else if ("已兑付".equals(status)) {
            return com.jc.R.drawable.honored_background;
        }

        return 0;
    }

}
