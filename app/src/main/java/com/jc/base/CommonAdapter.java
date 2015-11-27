package com.jc.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.jc.ui.CustomProgress;
import com.jc.ui.MainTabActivity;
import com.jc.utils.DateSerializerUtil;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by lrh on 23/7/15.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    private List<T> items;

    protected LayoutInflater inflater;

    protected Holder<T> holder;

    protected Handler handler = new Handler();

    protected ListView listView;

    protected Context context;

    protected CustomProgress pd;

    private Boolean isShowLoading = true;

    protected Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,
            new DateSerializerUtil()).setDateFormat(DateFormat.LONG).setPrettyPrinting()
            .create();

    public CommonAdapter(Context context,Holder<T> holder,ListView listView){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.holder = holder;
        this.listView = listView;
        pd = CustomProgress.show(context);
    }

    public CommonAdapter(Context context,Holder<T> holder,ListView listView,Boolean isShowLoading){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.holder = holder;
        this.listView = listView;
        this.isShowLoading = isShowLoading;
        if(this.isShowLoading){
            pd = CustomProgress.show(context);
        }
    }



    public void setData(List<T> items){
        this.items = items;
    }

    public CommonAdapter withHandler(Handler handler){
        this.handler = handler;
        return  this;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(getItemView(), null);
            holder = this.holder.build(convertView);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        T item = items.get(position);
        holder.setData(item);
        return convertView;
    }

    public Handler getHandler(){
        return handler;
    }

    public Holder getHolder(){
        return  holder;
    }

    public void attach(ResultData data){
        final BaseAdapter adapter = this;
        items = transferData(data);
        handler.post(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(adapter);

            }
        });
    }

    public void render(ResultData data){
        try{
            Object dt = data.getData();
            if("-2".equals(data.getErrCode())){
                loginout();
                return;
            }
            if(dt != null ){
                attach(data);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(items != null && !items.isEmpty()){
                        hideNotData(context);
                    }
                    if(isShowLoading){
                        pd.dismiss();
                    }

                }
            });
        }catch (Exception e){
            Log.d("transfer err", "", e);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(isShowLoading){
                        pd.dismiss();
                    }
                }
            });
        }

    }

    public void attach(List<T> data){
        final BaseAdapter adapter = this;
        items = data;
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(items != null && !items.isEmpty()){
                    hideNotData(context);
                }
                if(isShowLoading){
                    pd.dismiss();
                }
                listView.setAdapter(adapter);

            }
        });
    }

    protected  List<T> transferData(ResultData data){
        return Collections.EMPTY_LIST;
    };

    protected  int getItemView(){
        return 0;
    }


    private void loginout(){
        Activity ac = (Activity)context;
        CustomApplication app = (CustomApplication)ac.getApplication();
        app.removeUser();
        Intent intent = new Intent(context,MainTabActivity.class);
        intent.putExtra("mainTab","个人中心");
        ac.startActivity(intent);
    }

    private void hideNotData(Context context){
        Activity ac = (Activity) context;
        final  View notData = ac.findViewById(com.jc.R.id.notdata);

        handler.post(new Runnable() {
            @Override
            public void run() {
                if(notData != null ){
                    notData.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


}
