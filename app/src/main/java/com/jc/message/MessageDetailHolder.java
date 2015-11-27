package com.jc.message;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.base.Holder;
import com.jc.utils.DateFormatUtil;

/**
 * Created by lrh on 23/7/15.
 */
public class MessageDetailHolder implements Holder<Message> {

    LinearLayout msgDetailContent;
    TextView msgType;
    TextView msgTitle;
    TextView msgInfo;
    TextView msgDate;


    public MessageDetailHolder(){

    }

    @Override
    public Holder build(View view) {
        return null;
    }

    public void build(Activity ac){
        msgDetailContent = (LinearLayout) ac.findViewById(com.jc.R.id.msgDetailContent);
        msgType = (TextView) ac.findViewById(com.jc.R.id.msgType);
        msgTitle = (TextView) ac.findViewById(com.jc.R.id.msgTitle);
        msgInfo = (TextView) ac.findViewById(com.jc.R.id.msgInfo);
        msgDate = (TextView) ac.findViewById(com.jc.R.id.msgDate);
    }

    @Override
    public void setData(Message obj) {
        msgType.setText(obj.getMsgType());
        msgTitle.setText(obj.getTitle());
        msgInfo.setText(obj.getInfo());
        msgDate.setText(DateFormatUtil.date2String(obj.getSendTime(), DateFormatUtil.TIME_FORMAT_H));
        msgDetailContent.setVisibility(View.VISIBLE);
    }

}
