package com.jc.integrate;

import android.view.View;
import android.widget.TextView;

import com.jc.base.Holder;
import com.jc.R;
import com.jc.utils.DateFormatUtil;

/**
 * Created by zy on 15/9/2.
 */
public class IntegrationiHolder implements Holder<integral> {


    private TextView mlaiyuan,mjifenshu,mdate;

    public IntegrationiHolder(){

    }

    private IntegrationiHolder(View view){
        mlaiyuan = (TextView) view.findViewById(R.id.laiyuan);
        mjifenshu = (TextView) view.findViewById(R.id.jifenshu);
        mdate = (TextView) view.findViewById(R.id.date);

    }



    @Override
    public Holder build(View view) {

        return new IntegrationiHolder(view);
    }

    @Override
    public void setData(integral obj) {
        mlaiyuan.setText("" + obj.getSource());
        mjifenshu.setText(String.valueOf(obj.getAward().intValue()));
        mdate.setText(""+ DateFormatUtil.date2String(obj.getDate(), DateFormatUtil.DATE_FORMAT));
    }
}
