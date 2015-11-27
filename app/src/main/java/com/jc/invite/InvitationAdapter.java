package com.jc.invite;

import android.content.Context;
import android.widget.ListView;

import com.jc.base.CommonAdapter;
import com.jc.base.Holder;
import com.jc.base.ResultData;

import com.google.gson.reflect.TypeToken;
import com.jc.image.LoadImage;
import com.jc.utils.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by lrh on 6/8/15.
 */
public class InvitationAdapter extends CommonAdapter<Invitation> {

    private LoadImage loadImage;

    public InvitationAdapter(Context context, Holder<Invitation> holder, ListView listView,LoadImage loadImage){
        super(context,holder,listView);
        this.loadImage = loadImage;
    }

    protected List<Invitation> transferData(ResultData data){
        Map<String,Object>  rs = (Map<String, Object>) data.getData();
        Object pros = rs.get("invites");
        String encodeLink = (String) rs.get("encodeLink");
        if(!StringUtils.isEmpty(encodeLink) && loadImage != null){
            loadImage.load(encodeLink);
        }
        return gson.fromJson(gson.toJson(pros), new TypeToken<List<Invitation>>() {
        }.getType());
    }

    @Override
    protected int getItemView() {
        return com.jc.R.layout.invite_friends_item;
    }

}
