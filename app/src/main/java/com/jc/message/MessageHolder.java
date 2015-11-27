package com.jc.message;

import android.view.View;
import android.widget.TextView;

import com.jc.base.Holder;

/**
 * Created by lrh on 23/7/15.
 */
public class MessageHolder implements Holder<Message> {

    private static final String unRead = "未读";

    private static final String readed = "已读";

    TextView msgSatus;
    TextView msgTitle;
    TextView msgInfo;
    TextView msgDate;

    public MessageHolder(){

    }

    private MessageHolder(View view){
        msgSatus = (TextView) view.findViewById(com.jc.R.id.msgSatus);
        msgTitle = (TextView) view.findViewById(com.jc.R.id.msgTitle);
        msgInfo = (TextView) view.findViewById(com.jc.R.id.msgInfo);
        msgDate = (TextView) view.findViewById(com.jc.R.id.msgDate);
    }

    @Override
    public Holder build(View view) {
        return new MessageHolder(view);
    }

    @Override
    public void setData(Message obj) {
        if(readed.equals(obj.getIsRead())){
            msgSatus.setVisibility(View.GONE);
        }
        msgInfo.setText(obj.getInfo());

        msgTitle.setText(obj.getTitle());
//        msgDate.setText("34");
    }


}
