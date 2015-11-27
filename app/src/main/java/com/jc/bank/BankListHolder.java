package com.jc.bank;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.base.Holder;
import com.jc.utils.ResourceUtils;

/**
 * Created by lrh on 9/9/15.
 */
public class BankListHolder implements Holder<Bank> {
    public  static String CARD_LIMIT_INFO = "%s万/笔.%s万/日.%s万/月";

    ImageView bankICO;

    TextView bankName;

    TextView cardNo;

    TextView limitAmt;

    public BankListHolder(){

    }

    private BankListHolder(View view){
        bankICO = (ImageView) view.findViewById(com.jc.R.id.bankICO);
        bankName = (TextView) view.findViewById(com.jc.R.id.bankName);
        cardNo = (TextView) view.findViewById(com.jc.R.id.cardNo);
        limitAmt = (TextView) view.findViewById(com.jc.R.id.limitAmt);
    }

    @Override
    public Holder build(View view) {
        return new BankListHolder(view);
    }

    @Override
    public void setData(Bank obj) {
        bankICO.setImageResource(ResourceUtils.getMipmapResourceByReflect(obj.getBankCode().toLowerCase()));
        bankName.setText(obj.getName());
        cardNo.setText(obj.getCardNo());
        limitAmt.setText(String.format(CARD_LIMIT_INFO,obj.getOneLimitAmt(),obj.getDayLimitAmt(),obj.getMothLimitAmt()));
    }
}
