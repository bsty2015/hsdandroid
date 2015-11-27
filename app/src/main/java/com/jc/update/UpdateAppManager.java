package com.jc.update;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jc.customview.NumberProgressBar;
import com.jc.ui.MyProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 在安卓开发中,许多开发者都会遇到如何让应用程序自动更新的问题,
 * 也就是需要让应用程序自动发现服务器上的新版本然后提示更新,
 * 当用户点击确定后就下载新版本的应用程序,下载完毕后自动安装更新新程序
 * <p/>
 * 关于这个主要的流程是这样的：
 * 程序启动 --> 在适当的时候后台检查更新 --> 链接远程服务器 --> 获取服务器端新版本信息 --> 比对当前版本 --> 判断是否有更新，
 * 如果有有 --> 显示更新提示对话框并显示更新提示内容 --> 交与用户选择是否立即更新。
 * <p/>
 * 配置文件格式(xml格式为例)
 * <info>
 * <vision>1.0.0.1</vision>
 * <url>http://192.168.0.1:8080/appupt/</url>
 * <description>新版本描述信息</description>
 * </info>
 * Created by bsty on 11/17/15.
 */
public class UpdateAppManager {

    //文件分隔符
    private static final String FILE_SEPARATOR = "/";

    //外村sdcard存放路径
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + FILE_SEPARATOR + "autoupdate" + FILE_SEPARATOR;

    //下载应用存放全路径
    private static final String FILE_NAME = FILE_PATH + "autoupdate.apk";
    //更新应用版本标记
    private static final int UPDATE_TOKEN = 0x29;
    //准备安装新版本应用标记
    private static final int INSTALL_TOKEN = 0x31;

    private Context context;

    private NumberProgressBar progressBar;
    //进度条当前刻度值
    private int curProgress;
    //用户是否取消下载
    private boolean isCancel;

    private RelativeLayout view;

    public UpdateAppManager(Context context, NumberProgressBar progressBar, RelativeLayout view) {
        this.context = context;
        this.progressBar = progressBar;
        this.view = view;
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TOKEN:
                    view.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(curProgress);
                    break;
                case INSTALL_TOKEN:
                    progressBar.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
                    installApp();
                    break;
                default:
                    break;
            }
        }
    };

    private void installApp() {
        File appFile = new File(FILE_NAME);
        if (!appFile.exists()) {
            return;
        }
        //跳转到新版本应用安装界面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + appFile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public String getCurVersion() throws PackageManager.NameNotFoundException {
        //获取PackageManager实例
        PackageManager packageManager = context.getPackageManager();
        //获得context所属类的包名，0表示获取版本信息
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionName;
    }

    public void downloadApp(final String downlaodUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                InputStream in = null;
                FileOutputStream out = null;
                HttpURLConnection conn = null;
                try {
                    url = new URL(downlaodUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    long fileLength = conn.getContentLength();
                    in = conn.getInputStream();
                    File filePath = new File(FILE_PATH);
                    if (!filePath.exists()) {
                        filePath.mkdir();
                    }
                    out = new FileOutputStream(FILE_NAME);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    long readedLength = 0l;
                    while ((len = in.read(buffer)) != -1) {
                        if (isCancel) {
                            break;
                        }
                        out.write(buffer, 0, len);
                        readedLength += len;
                        curProgress = (int) (((float) readedLength / fileLength) * 100);
                        handler.sendEmptyMessage(UPDATE_TOKEN);
                        if (readedLength >= fileLength) {
//                            dialog.dismiss();
                            //下载完成，通知安装
                            handler.sendEmptyMessage(INSTALL_TOKEN);
                            break;
                        }
                    }
                    out.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }


}
