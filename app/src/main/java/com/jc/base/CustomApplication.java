package com.jc.base;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jc.R;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.jc.user.User;
import com.jc.user.UserInfo;
import com.jc.utils.StoreUtils;
import com.jc.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lrh on 15/8/15.
 */
public class CustomApplication extends ActivityManagerApplication {

    private RefWatcher refWatcher;

    public static String accessToken = "";

    public static Map<String, String> info;

    public static RequestQueue mQueue;

    public static UserInfo userInfo;

    public static User user;

    private static StoreUtils store;

    private static String firstStartup = null;

    private WeakHashMap<String, Object> data = new WeakHashMap<String, Object>();


    public static RefWatcher getRefWatcher(Context context) {
        CustomApplication application = (CustomApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Log.w("RegistrationID:", JPushInterface.getRegistrationID(this));
        createNotification(this, JPushInterface.getRegistrationID(this));
        mQueue = Volley.newRequestQueue(this);
        store = new StoreUtils(this);
        firstStartup = store.getValue("firstStartup");
        if (StringUtils.isEmpty(firstStartup)) {
            store.put("firstStartup", "firstStartup");
        }
        user = store.getUser();
        if (user != null) {
            accessToken = user.getAccessToken();
        }

        if (info == null) {
            info = new HashMap<>();
            info.put("osVersion", android.os.Build.VERSION.RELEASE);
            info.put("osName", android.os.Build.MODEL);


            PackageManager manager = this.getPackageManager();

            try {
                PackageInfo packageInfo = manager.getPackageInfo(this.getPackageName(), 0);
                ApplicationInfo appInfo = manager.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);

                info.put("channel", appInfo.metaData.getString("CHANNEL"));
                info.put("appVersion", packageInfo.versionName); // 版本名，versionCode同理
                String av = packageInfo.versionName;
                String appVersion = store.getValue("appVersion");
                if (StringUtils.isEmpty(appVersion)) {
                    store.put("appVersion", packageInfo.versionName);
                } else if (!packageInfo.versionName.equals(appVersion)) {
                    firstStartup = null;
                    store.put("appVersion", packageInfo.versionName);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            try {
                TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
                info.put("deviceId", telephonyManager.getDeviceId());
            } catch (Exception e) {

            }

        }
        refWatcher = RefWatcher.DISABLED;

    }

    public Boolean isFirstStartUp() {
        return firstStartup == null || StringUtils.isEmpty(firstStartup);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public CustomApplication put(String key, String value) {
        data.put(key, value);
        return this;
    }

    public CustomApplication put(Map<String, Object> map) {
        data.putAll(map);
        return this;
    }

    public Integer getUserId() {
        if (user != null) {
            return user.getId();
        }
        return -1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            accessToken = user.getAccessToken();
        }
        store.saveUser(user);
    }

    public void removeUser() {
        this.user = null;
        accessToken = "";
        new StoreUtils(this).removeUser();
    }

    /**
     * 检查是否已经登录
     *
     * @return
     */
    public static boolean isLogined() {
        if (user != null && accessToken != null) {
            return true;
        }
        return false;
    }

    private void createNotification(Context context, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("RegistrationID")
                .setContentText(content);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(666, builder.build());
    }

    public void removeKey(String key) {
        store.remove(key);
    }
}
