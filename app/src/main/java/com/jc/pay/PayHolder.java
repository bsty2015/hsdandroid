package com.jc.pay;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.bank.AddCardActivity;
import com.jc.bank.Card;
import com.jc.base.Configure;
import com.jc.base.Holder;
import com.jc.ui.WebViewActivity;
import com.jc.utils.ApplicationUtils;
import com.jc.utils.RateUtils;
import com.jc.utils.RegexUtil;
import com.jc.utils.ResourceUtils;
import com.jc.utils.StringUtils;

import java.math.BigDecimal;

import com.jc.utils.NumberUtils;

/**
 * Created by lrh on 9/9/15.
 */
public class PayHolder implements Holder<ProductPayInfo> {

    private ProductPayInfo productPayInfo;

    TextView remainAmt;

    TextView productName;

    TextView rate;

    TextView duration;

    private LinearLayout notBindCard;

    private LinearLayout bindCardBar;

    private ImageView cardICON;

    private TextView cardLast;

    private TextView cardName;

    private EditText investAmtView;

    private TextView profit;

    private LinearLayout firstPayInfo;

    private LinearLayout repeatPayInfo;

    private TextView repeatProfit;

    private TextView debateContract;

    private LinearLayout payContentView;

    private  Activity ac;





    public PayHolder(final Activity ac){
        this.ac = ac;
        remainAmt = (TextView) ac.findViewById(com.jc.R.id.remainAmt);
        productName = (TextView) ac.findViewById(com.jc.R.id.productName);
        rate = (TextView) ac.findViewById(com.jc.R.id.rate);
        duration = (TextView) ac.findViewById(com.jc.R.id.payProDuration);
        cardICON = (ImageView) ac.findViewById(com.jc.R.id.cardICON);
        cardLast = (TextView) ac.findViewById(com.jc.R.id.cardLast);
        cardName = (TextView) ac.findViewById(com.jc.R.id.cardName);
        notBindCard = (LinearLayout) ac.findViewById(com.jc.R.id.notBindCard);
        bindCardBar = (LinearLayout) ac.findViewById(com.jc.R.id.bindCardBar);
        investAmtView = (EditText) ac.findViewById(com.jc.R.id.investAmt);
        profit = (TextView) ac.findViewById(com.jc.R.id.profit);
        repeatProfit = (TextView) ac.findViewById(com.jc.R.id.repeatProfit);
        firstPayInfo = (LinearLayout) ac.findViewById(com.jc.R.id.firstPayInfo);
        repeatPayInfo = (LinearLayout) ac.findViewById(com.jc.R.id.repeatPayInfo);
        debateContract = (TextView) ac.findViewById(com.jc.R.id.debateContract);
        payContentView = (LinearLayout) ac.findViewById(com.jc.R.id.payContentView);
        listenerInvestAmt(investAmtView, profit);
        bindCardBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardListWindow cardListWindow = new CardListWindow(ac, productPayInfo.getOwnCards(), PayHolder.this);
                cardListWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                cardListWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                cardListWindow.showAtLocation(ac.findViewById(com.jc.R.id.topLayout), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });



    }

    @Override
    public Holder build(View view) {
        return null;
    }

    @Override
    public void setData(final ProductPayInfo obj) {
        productPayInfo = obj;
        ProductPayInfo.Product pro = obj.getProduct();
        Card card = obj.getCard();
        productName.setText(pro.getName());
        rate.setText(String.format("预期年化收益率%s", NumberUtils.formatNormalNumber(pro.getRate()))+"%");
        duration.setText(String.format("期限%s天", pro.getDuration()));
        remainAmt.setText(NumberUtils.formatNumber(obj.getRemainAmt()));

        if(card == null){
            notBindCard.setVisibility(View.VISIBLE);
            bindCardBar.setVisibility(View.GONE);
        }else {
            notBindCard.setVisibility(View.GONE);
            bindCardBar.setVisibility(View.VISIBLE);
            changCard(card);
        }
        if(obj.getInvestAmt() != null){
            firstPayInfo.setVisibility(View.GONE);
            repeatPayInfo.setVisibility(View.VISIBLE);
            repeatProfit.setText(NumberUtils.formatNumber(obj.getProfit()));
            investAmtView.setText(String.valueOf(obj.getInvestAmt().intValue()));
            investAmtView.setKeyListener(null);
        }else{
            firstPayInfo.setVisibility(View.VISIBLE);
            repeatPayInfo.setVisibility(View.GONE);
        }

        debateContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String investAmt = investAmtView.getText().toString();
                    if (StringUtils.isEmpty(investAmt)) {
                        investAmt = "0";
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("productId=").append(obj.getProduct().getId());

                    sb.append("&investAmt=").append(investAmt);
                    sb.append("&userId=").append(ApplicationUtils.getUserId(ac));
                    sb.append("&accessToken=").append(ApplicationUtils.getAccessToken(ac));
                    Intent intent = new Intent(ac, WebViewActivity.class);
                    intent.putExtra("title","应收账款转让及回购协议");
                    intent.putExtra("url", Configure.H5_HOST + "/pay/contract");
                    intent.putExtra("data", sb.toString());
                    ac.startActivity(intent);
                } catch (Exception e) {

                }
            }
        });

        notBindCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.startActivity(new Intent(ac,AddCardActivity.class));
            }
        });
        payContentView.setVisibility(View.VISIBLE);
    }

    private void listenerInvestAmt(final EditText investAmtView,final TextView profit){

        investAmtView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tmp = investAmtView.getText().toString();
                if(RegexUtil.match(tmp, "^0")){
                    investAmtView.setText("");
                    profit.setText("0");
                    return;
                }
                if (!StringUtils.isEmpty(tmp)) {
                    profit.setText(NumberUtils.formatNumber(RateUtils.calculateProfit(new BigDecimal(tmp), productPayInfo.getProduct().getDuration(), productPayInfo.getProduct().getRate())));
                }else{
                    profit.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void changCard(Card card){
        cardICON.setImageResource(ResourceUtils.getMipmapResourceByReflect(card.getBankCode().toLowerCase()));
        cardLast.setText(card.getCardLast());
        cardName.setText(card.getBankName());
        ((PayActivity)this.ac).setSelectedCardId(card.getId());
    }

    public Integer getSelectedCardId(){
        return  ((PayActivity)this.ac).getSelectedCardId();
    }


}
