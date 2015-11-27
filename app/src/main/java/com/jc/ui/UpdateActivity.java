package com.jc.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.R;
import com.jc.customview.NumberProgressBar;
import com.jc.update.ParseXmlUtils;
import com.jc.update.UpdateAppManager;
import com.jc.update.UpdateInfo;
import com.jc.utils.XMLRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zy on 15/11/17.
 */
public class UpdateActivity extends HeadMenuActiviyt {

    private static final String UPDATE_URL = "http://192.168.100.109:8080/jicai.json";
    private UpdateInfo updateInfo;
    //    private TextView mVersionUpdateInfo;
//    private MyProgressBar mVersionUpdateInfo;
    private NumberProgressBar downloadProgressBar;
    private UpdateAppManager updateAppManager;
    private LinearLayout updateBtnLayout;
    private RelativeLayout progressed;
    private TextView isAppNeedDownloadInfo;
    //是否有新版本标记 默认没有新版本
    private boolean isHaveNewVersion = false;
    private boolean isCheckVersionFinished = false;
    private String downloadUrl;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_app);
        setHeadTitleName("当前版本");
        downloadProgressBar = (NumberProgressBar) findViewById(R.id.show_version_update_info);
        updateBtnLayout = (LinearLayout) findViewById(R.id.update_btn_layout);
        isAppNeedDownloadInfo = (TextView) findViewById(R.id.app_is_need_download_info);
        progressed = (RelativeLayout) findViewById(R.id.progressed);
        updateAppManager = new UpdateAppManager(this, downloadProgressBar, progressed);
        checkVersion();

        updateBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckVersionFinished && isHaveNewVersion) {
//                    progressed.setVisibility(View.GONE);
                    downloadApp();

                }

            }
        });

        initView();

    }

    /**
     * 检查是否需要更新
     */
//    private void checkVersion() {
//        StringRequest stringRequest = new StringRequest(UPDATE_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //解析response获取的json，并实例化到updateInfo对象里，便于以后取值
//                Gson gson = new Gson();
//                Type type = new TypeToken<UpdateInfo>() {
//                }.getType();
//                String url = "";
//                updateInfo = gson.fromJson(response, type);
//                if (updateInfo != null) {
//                    url = updateInfo.getUrl();
//                    Log.w("下载的URL:", url);
//                    //检查版本信息是否需要更新
//                    try {
//                        if (updateInfo.getVersion().equals(updateAppManager.getCurVersion())) {
////                            showErrMsg("已经是最新版本");
//                            isHaveNewVersion = false;
//                            isCheckVersionFinished = true;
//                            progressed.setVisibility(View.VISIBLE);
//                            isAppNeedDownloadInfo.setText(R.string.zuixin);
//                        } else {
//                            progressed.setVisibility(View.VISIBLE);
//                            isHaveNewVersion = true;
//                            downloadUrl = url;
//                            isAppNeedDownloadInfo.setText(R.string.xiazai);
//                            isAppNeedDownloadInfo.setTextColor(getResources().getColor(R.color.coral));
//                            isCheckVersionFinished = true;
//                        }
//                    } catch (PackageManager.NameNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("更新出错:", error.getMessage());
//            }
//        });
//        mQueue.add(stringRequest);
//    }
    private void checkVersion(){

    }

    private void downloadApp() {
        //有最新版本，开始下载更新
        if (downloadUrl != null && !downloadUrl.isEmpty()) {
            updateAppManager.downloadApp(downloadUrl);
        }
    }
}
