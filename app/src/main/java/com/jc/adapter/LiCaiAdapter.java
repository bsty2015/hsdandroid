package com.jc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by zy on 15/7/17.
 */
public class LiCaiAdapter extends BaseAdapter{
    @Override
    public int getCount() {
        return 13;
    }

    public LiCaiAdapter(){

        super();
    }
    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        if (convertView==null){
        convertView = LayoutInflater.from (parent.getContext()).inflate(com.jc.R.layout.produce_item, null);
        holder = new Holder();
        holder.name = (TextView) convertView.findViewById(com.jc.R.id.name);
        holder.interestRate = (TextView) convertView.findViewById(com.jc.R.id.interestRate);
        holder.collectDuration = (TextView) convertView.findViewById(com.jc.R.id.collectDuration);
        holder.remainAmt = (TextView) convertView.findViewById(com.jc.R.id.remainAmt);
        convertView.setTag(holder);
       } else {
           holder = (Holder) convertView.getTag();
         }
        return convertView;
    }


    private class Holder{
        private TextView name;
        private TextView interestRate;
        private TextView collectDuration;
        private TextView remainAmt;
    }
}
