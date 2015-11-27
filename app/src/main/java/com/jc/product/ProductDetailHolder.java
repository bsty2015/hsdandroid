package com.jc.product;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jc.base.Holder;
import com.jc.R;
import com.jc.pay.PayActivity;

import java.math.BigDecimal;

import static com.jc.utils.NumberUtils.formatNormalNumber;
import static com.jc.utils.NumberUtils.formatNumber;

/**
 * Created by lrh on 23/7/15.
 */
public class ProductDetailHolder implements Holder<ProductDetail> {

    TextView name;
    TextView interestRate;
    TextView collectDuration;
    TextView remainAmt;
    TextView extraRate;
    TextView totalAmt;
    View proDetailHead;
    View payButton;

    TextView statusInfo;

    TextView proLimitAmt;

    RelativeLayout proDetailContent;

    private LinearLayout extraAdd;

    private LinearLayout newShare;

    private ProductDetail productDetail;

    private Boolean isCanPay = Boolean.FALSE;


    public ProductDetailHolder(){

    }

    @Override
    public Holder build(View view) {
        return null;
    }

    public void build(final Activity ac){
        name = (TextView) ac.findViewById(R.id.proDetailName);
        interestRate = (TextView) ac.findViewById(R.id.proDetailRate);
        collectDuration = (TextView) ac.findViewById(R.id.proDetailDuration);
        totalAmt = (TextView) ac.findViewById(R.id.proDetailAmt);
        remainAmt = (TextView) ac.findViewById(R.id.proDetailRemaindAmt);
        extraRate = (TextView) ac.findViewById(R.id.proDetailExtRate);
        proDetailHead = ac.findViewById(R.id.proDetailHead);
        extraAdd = (LinearLayout) ac.findViewById(R.id.extraAdd);
        newShare = (LinearLayout) ac.findViewById(R.id.newShare);
        statusInfo = (TextView) ac.findViewById(R.id.statusInfo);
        proLimitAmt = (TextView) ac.findViewById(R.id.proLimitAmt);

        payButton = ac.findViewById(R.id.payButton);

        proDetailContent = (RelativeLayout) ac.findViewById(R.id.proDetailContent);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(isCanPay && productDetail != null){
                        Intent intent = new Intent(ac,PayActivity.class);
                        intent.putExtra("productId",productDetail.getId());
                        ac.startActivity(intent);
                    }
            }
        });

    }

    @Override
    public void setData(ProductDetail obj) {
        proDetailContent.setVisibility(View.VISIBLE);
        productDetail = obj;
        String status = obj.getStatus();
        if("兑付中".equals(status)){
            status = "售完";
        }

        setHeadBackgroupClolor(status,obj.getLimitAmt());
        name.setText(obj.getName());
        collectDuration.setText(obj.getCollectDuration().toString());
        remainAmt.setText(formatNumber(obj.getRemianAmt()));
        interestRate.setText(formatNumber(obj.getInterestRate())+"%");
        totalAmt.setText(formatNumber(obj.getAmt()));
        BigDecimal eRate = obj.getExtraInterestRate();
        if(eRate != null ){
            extraRate.setText("+"+formatNormalNumber(eRate)+"%");
        }
        if(eRate != null){
            extraAdd.setVisibility(View.VISIBLE);
        }
        if("是".equals(obj.getIsNew())){
            newShare.setVisibility(View.VISIBLE);
        }


    }



    private void setHeadBackgroupClolor(String status,BigDecimal limitAmt){
        switch (status){
            case "销售中":
                isCanPay = Boolean.TRUE;
                payStatus("购买",limitAmt);
                proDetailHead.setBackgroundResource(R.color.best_sellerr);
                payButton.setBackgroundResource(R.color.best_sellerr);
                break;
            case "待上架":
                payStatus("待上架",null);
                proDetailHead.setBackgroundResource(R.color.waterblue);
                payButton.setBackgroundResource(R.color.waterblue);
                break;
            case "售完":
                payStatus("售完",null);
                proDetailHead.setBackgroundResource(R.color.sell_outt);
                payButton.setBackgroundResource(R.color.sell_outt);
                break;
            case "已兑付":
                payStatus("已兑付",null);
                proDetailHead.setBackgroundResource(R.color.advance_booking);
                payButton.setBackgroundResource(R.color.advance_booking);
                break;
            default:
                break;

        }

    }

    private void payStatus(String msg,BigDecimal limitAmt){
        statusInfo.setText(msg);
        if(limitAmt == null ){
            proLimitAmt.setVisibility(View.GONE);
        }else {
            proLimitAmt.setText(String.format("／%s元起购",formatNumber(limitAmt)));
        }

    }

}
