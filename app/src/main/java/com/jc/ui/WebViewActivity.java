package com.jc.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jc.R;
import com.jc.utils.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by zy on 15/8/3.
 */
public class WebViewActivity extends HeadMenuActiviyt implements FreshenList.OnRefreshListener {

    private Intent intent;

    private WebView webView;

    private CustomProgress progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        initView();
        webView = (WebView) findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
//        设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
//        可以读取文件缓存(manifest生效)
        webView.getSettings().setAllowFileAccess(true);
        intent = getIntent();
        String title = intent.getStringExtra("title");
        if (!StringUtils.isEmpty(title)) {
            setHeadTitleName(title);
        } else {
            setHeadTitleName("");
        }
        String data = intent.getStringExtra("data");
        String url = intent.getStringExtra("url");
        if (StringUtils.isEmpty(data)) {
            webView.loadUrl(url);
        } else {
            try {
                webView.postUrl(url, data.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {// 网页页面开始加载的时候


                if (progressDialog == null) {
                    progressDialog = CustomProgress.show(WebViewActivity.this);
                    webView.setEnabled(false);// 当加载网页的时候将网页进行隐藏
                }
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {// 网页加载结束的时候
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                    webView.setEnabled(true);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);// 使用当前WebView处理跳转
                return true;//true表示此事件在此处被处理，不需要再广播
            }

            @Override   //转向错误时的处理
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
            }
        });
        findViewById(R.id.headBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onRefresh() {

    }
}