package com.jc.capital;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.base.Configure;
import com.jc.base.Holder;
import com.jc.utils.ApplicationUtils;
import com.jc.ui.WebViewActivity;
import com.jc.utils.DateFormatUtil;
import com.jc.utils.ResourceUtils;

import static com.jc.utils.NumberUtils.formatNumber;

/**
 * Created by lrh on 29/8/15.
 */
public class InvestProDetailHolder implements Holder<InvestProDetail> {

    private TextView orderId;

    private TextView productName;

    private TextView proCorpus;

    private TextView proProfit;

    private TextView investDate;

    private ImageView investICON;

    private TextView repayDate;

    private ImageView repayICON;

    private ImageView bankICO;

    private TextView bankInfo;

    private LinearLayout contract;

    private Activity ac;


    public InvestProDetailHolder(){

    }

    @Override
    public Holder build(View view) {
        return null;
    }

    public void build(Activity ac) {
        this.ac = ac;
        orderId = (TextView) ac.findViewById(com.jc.R.id.orderId);
        productName = (TextView) ac.findViewById(com.jc.R.id.productName);
        proCorpus = (TextView) ac.findViewById(com.jc.R.id.proCorpus);
        proProfit = (TextView) ac.findViewById(com.jc.R.id.proProfit);
        investDate = (TextView) ac.findViewById(com.jc.R.id.investDate);
        repayDate = (TextView) ac.findViewById(com.jc.R.id.repayDate);
        bankInfo = (TextView) ac.findViewById(com.jc.R.id.bankInfo);
        investICON = (ImageView) ac.findViewById(com.jc.R.id.investICON);
        repayICON = (ImageView) ac.findViewById(com.jc.R.id.repayICON);
        bankICO = (ImageView) ac.findViewById(com.jc.R.id.bankICO);
        contract = (LinearLayout) ac.findViewById(com.jc.R.id.contract);
        setContractListener(contract);
    }

    @Override
    public void setData(InvestProDetail obj) {
        orderId.setText(obj.getOrderId());
        productName.setText(obj.getProductName());
        proCorpus.setText(formatNumber(obj.getCorpus()));
        proProfit.setText(formatNumber(obj.getProfit()));
        bankICO.setImageResource(ResourceUtils.getMipmapResourceByReflect(obj.getBankCode().toLowerCase()));
        bankInfo.setText(String.format("%s(尾号%s)",obj.getBankName(),obj.getCardLast()));
        investDate.setText(DateFormatUtil.date2String(obj.getInvestDate(), DateFormatUtil.TIME_FORMAT_A)+"购买");
        if("已还款".equals(obj.getStatus())){
            repayDate.setText(String.format("已于%s兑付",DateFormatUtil.date2String(obj.getExpireDate(),DateFormatUtil.DATE_FORMAT)));
            repayICON.setImageResource(ResourceUtils.getMipmapResourceByReflect("compelet"));
        }else {
            repayDate.setText(String.format("将于%s兑付",DateFormatUtil.date2String(obj.getExpireDate(),DateFormatUtil.DATE_FORMAT)));
        }
    }

    private void setContractListener(LinearLayout contract){
        contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ac,WebViewActivity.class);
                intent.putExtra("title","应收账款转让协议");
                intent.putExtra("url", Configure.H5_HOST+"/contract/"+orderId.getText());
                intent.putExtra("data","accessToken="+ ApplicationUtils.getAccessToken(ac));
                ac.startActivity(intent);
            }
        });
    }
}
