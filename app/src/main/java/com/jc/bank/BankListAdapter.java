package com.jc.bank;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.jc.base.CommonAdapter;
import com.jc.base.Holder;
import com.jc.base.ResultData;

import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Created by lrh on 6/8/15.
 */
public class BankListAdapter extends CommonAdapter<Bank> {

    ImageView notCard;

    public BankListAdapter(Context context, Holder<Bank> holder, ListView listView){
        super(context,holder,listView);
        notCard = (ImageView) ((Activity)context).findViewById(com.jc.R.id.notCard);
    }

    protected List<Bank> transferData(ResultData data){
        Map<String,Object>  rs = (Map<String, Object>) data.getData();
        Object pros = rs.get("cards");
        List<Bank> banks = gson.fromJson(gson.toJson(pros), new TypeToken<List<Bank>>() {}.getType());
        controllerDataShow(banks);
        return banks;
    }

    @Override
    protected int getItemView() {
        return com.jc.R.layout.addbank_item;
    }

    private void controllerDataShow(List<Bank> banks){
        if(banks != null && !banks.isEmpty()){
            notCard.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }

}
