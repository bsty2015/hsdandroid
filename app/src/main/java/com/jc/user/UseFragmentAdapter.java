package com.jc.user;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.jc.base.CommonAdapter;
import com.jc.base.CustomApplication;
import com.jc.base.Holder;
import com.jc.base.ResultData;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by zy on 15/8/11.
 */
public class UseFragmentAdapter  extends CommonAdapter<UserInfo> {


//    private Gson gson;


    public UseFragmentAdapter(Context context, Holder<UserInfo> holder, ListView listView){
        super(context,holder,listView);
//        gson =new Gson();
    }

    public void attach(ResultData data){
        Map<String,Object> rs = (Map<String, Object>) data.getData();
        Object userObj = rs.get("userInfo");
        final UserInfo userInfo = gson.fromJson(gson.toJson(userObj), UserInfo.class);
        handler.post(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
                ((UserFragmentHoldr) holder).build(null);
                ((UserFragmentHoldr) holder).setData(userInfo);
            }
        });
        CustomApplication app = (CustomApplication) ((Activity)context).getApplication();
        app.userInfo = userInfo;
        User user = app.getUser();
        if(user != null && user.getUserKey() == null ){
            user.setUserKey(userInfo.getUserKey());
            app.setUser(user);
        }

    }






}
