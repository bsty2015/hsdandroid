package com.jc.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.bank.BankListActivity;
import com.jc.base.Holder;
import com.jc.capital.UnpayCapitalActivity;
import com.jc.integrate.IntegrationActivity;
import com.jc.account.AccountActivity;
import com.jc.capital.CapitalActivity;
import com.jc.message.MessageActivity;
import com.jc.invite.InviteActivity;
import com.jc.cash.CashActivity;
import com.jc.utils.NumberUtils;

/**
 * Created by zy on 15/8/10.
 */
public class UserFragmentHoldr<U> implements Holder<UserInfo> {
    private Context context;
    private TextView massets,mtodayncome,mincome;
    private LinearLayout maccount, unpayOrderBar,mzichan, cashBar,mjifen, bankBar, inviteBar, messageBar;

    private ImageView isExistMsgTip;

    private TextView mnotnum,mcasmt,mcreditsnum,mcardnum;
    public  UserFragmentHoldr(Context context){
        this.context = context;
    }

    @Override
    public Holder build(View view) {
        Activity ac = (Activity)context;
        massets = (TextView) ac.findViewById(com.jc.R.id.assets);
        mtodayncome = (TextView) ac.findViewById(com.jc.R.id.today_income);
        mincome = (TextView) ac.findViewById(com.jc.R.id.accumulated_income);

        maccount = (LinearLayout) ac.findViewById(com.jc.R.id.account_information);
        unpayOrderBar = (LinearLayout) ac. findViewById(com.jc.R.id.unpayOrder);
        mzichan = (LinearLayout) ac.findViewById(com.jc.R.id.zichan);
        cashBar = (LinearLayout) ac.findViewById(com.jc.R.id.lijin);
        mjifen = (LinearLayout) ac.findViewById(com.jc.R.id.jifen);
        bankBar = (LinearLayout) ac.findViewById(com.jc.R.id.yinhang);
        inviteBar = (LinearLayout)ac. findViewById(com.jc.R.id.yaoqhy);
        messageBar = (LinearLayout) ac.findViewById(com.jc.R.id.xiaoxi);//消息

        isExistMsgTip = (ImageView) ac.findViewById(com.jc.R.id.isExistMsgTip);

        mnotnum = (TextView) ac.findViewById(com.jc.R.id.notnum); //未完成订单
        mcasmt = (TextView) ac.findViewById(com.jc.R.id.caamt);   //礼金
        mcreditsnum = (TextView) ac.findViewById(com.jc.R.id.creditsnum);   //积分数
        mcardnum = (TextView) ac.findViewById(com.jc.R.id.cardnum);   //银行卡数

        init();
        return this;
    }


    @Override
    public void setData(UserInfo obj) {
      massets.setText(NumberUtils.formatNumber(obj.getTotalAssetAmt()));
      mtodayncome.setText(NumberUtils.formatNumber(obj.getTodayTotalProfit()));
      mincome.setText(NumberUtils.formatNumber(obj.getTotalprofitAmt()));

      mnotnum.setText(""+obj.getUnpayNum());
      mcasmt.setText(NumberUtils.formatNumber(obj.getCashAmt()));

      mcreditsnum.setText(""+obj.getCreditsNum());
      mcardnum.setText(""+ obj.getCardNum());
      if(obj.getIsExistMsg()){
          isExistMsgTip.setVisibility(View.VISIBLE);
      }else {
          isExistMsgTip.setVisibility(View.INVISIBLE);
      }
    }

    private void init(){

        maccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AccountActivity.class);
                context.startActivity(intent);
            }
        });


        unpayOrderBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UnpayCapitalActivity.class);
                context.startActivity(intent);
            }
        });

        mzichan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CapitalActivity.class);
                context. startActivity(intent);
            }
        });

        cashBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CashActivity.class);
                context.startActivity(intent);
            }
        });



        bankBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BankListActivity.class);
                context.startActivity(intent);
            }
        });

        mjifen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,IntegrationActivity.class);
                context.startActivity(intent);
            }
        });


        inviteBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InviteActivity.class);
                context.startActivity(intent);
            }
        });



        messageBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                context.startActivity(intent);
            }
        });

    }
}
