package com.jc.pay;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.bank.Card;
import com.jc.base.Holder;
import com.jc.utils.ResourceUtils;

/**
 * Created by lrh on 9/9/15.
 */
public class CardListHolder implements Holder<Card> {
    public  static String CARD_LIMIT_INFO = "%s万/笔.%s万/日.%s万/月";

    ImageView cardICON;

    TextView bankName;

    TextView cardLast;

    TextView limitAmt;

    ImageView selectedView;

    private static Integer selectedId;

    public CardListHolder(Integer selectedId){
            this.selectedId = selectedId;
    }

    private CardListHolder(View view){
        cardICON = (ImageView) view.findViewById(com.jc.R.id.cardICON);
        bankName = (TextView) view.findViewById(com.jc.R.id.bankName);
        cardLast = (TextView) view.findViewById(com.jc.R.id.cardLast);
        limitAmt = (TextView) view.findViewById(com.jc.R.id.limitAmt);
        selectedView = (ImageView) view.findViewById(com.jc.R.id.selectedView);
    }

    @Override
    public Holder build(View view) {
        return new CardListHolder(view);
    }

    @Override
    public void setData(Card obj) {
        cardICON.setImageResource(ResourceUtils.getMipmapResourceByReflect(obj.getBankCode().toLowerCase()));
        bankName.setText(obj.getBankName());
        cardLast.setText(obj.getCardLast());
        limitAmt.setText(String.format(CARD_LIMIT_INFO,obj.getOneLimitAmt(),obj.getDayLimitAmt(),obj.getMothLimitAmt()));
        if(obj.getId().equals(selectedId)){
            selectedView.setVisibility(View.VISIBLE);
        }else {
            selectedView.setVisibility(View.INVISIBLE);
        }
    }
}
