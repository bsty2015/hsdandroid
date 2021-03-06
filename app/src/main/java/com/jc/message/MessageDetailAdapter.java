package com.jc.message;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.jc.base.CommonAdapter;
import com.jc.base.Holder;
import com.jc.base.ResultData;

import java.util.Map;

/**
 * Created by lrh on 6/8/15.
 */
public class MessageDetailAdapter extends CommonAdapter<Message> {

    public MessageDetailAdapter(Context context, Holder<Message> holder, ListView listView){
        super(context,holder,listView);
    }

    public void attach(ResultData data){
        Map<String,Object>  rs = (Map<String, Object>) data.getData();
        Object pro = rs.get("messageItem");
        final Message messageDetail = gson.fromJson(gson.toJson(pro), Message.class);
        final Activity ac = (Activity) context;
        handler.post(new Runnable() {
            @Override
            public void run() {
                ((MessageDetailHolder)holder).build(ac);
                ((MessageDetailHolder) holder).setData(messageDetail);
            }
        });
    }

}
