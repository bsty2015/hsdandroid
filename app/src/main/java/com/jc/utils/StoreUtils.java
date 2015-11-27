package com.jc.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jc.base.Configure;
import com.jc.user.User;

/**
 * Created by lrh on 15/8/15.
 */
public class StoreUtils {

    private static final String USER_KEY = "userInfo";
    private SharedPreferences store;


    public StoreUtils(Context context){
        store = context.getSharedPreferences("jcstore", Context.MODE_PRIVATE);
    }

    public StoreUtils put(String key,String value){
        SharedPreferences.Editor edit = store.edit();
        edit.putString(key, value);
        edit.commit();
        return this;
    };

    public StoreUtils remove(String key){
        SharedPreferences.Editor edit = store.edit();
        edit.remove(key);
        edit.commit();
        return this;
    }

    public String getValue(String key){
        return store.getString(key,"");
    }

    public User getUser(){
        String userInfo = getValue(USER_KEY);
        if(StringUtils.isEmpty(userInfo)){
            return null;
        }
        return (User)GsonUtil.toObject(AES.decryptFromBase64(userInfo, Configure.key), User.class);
    }

    public void saveUser(User user){
        put(USER_KEY,AES.encryptToBase64(GsonUtil.toString(user),Configure.key));
    }

    public void removeUser(){
        remove(USER_KEY);
    }
}
