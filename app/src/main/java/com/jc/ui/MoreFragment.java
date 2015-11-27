package com.jc.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.R;
import com.jc.utils.UmengShareUtils;

/**
 * Created by zy on 15/8/3.
 */
public class MoreFragment extends CommonFragment{
     private LinearLayout mguanyu,mkehu,mwenti,mhuodong,mfenxiang,mfankui,mgenxin;
    private UmengShareUtils umengShareUtils;
     private TextView mkefu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.more_about, container, false);
        umengShareUtils = new UmengShareUtils(getActivity(), "华盛达控股集团旗下互联网金融理财平台。即日起息，到期自动兑付，注册即送20元礼金和1000积分。");
        setHeadTitleName("了解更多");
        hideBackButton();
         mguanyu = (LinearLayout) rootView.findViewById(R.id.guanyu);
         mkehu = (LinearLayout) rootView.findViewById(R.id.kefu);
         mwenti = (LinearLayout) rootView.findViewById(R.id.chjian);
         mhuodong = (LinearLayout) rootView.findViewById(R.id.huodong);
         mfenxiang = (LinearLayout) rootView.findViewById(R.id.fenxiang);
         mfankui = (LinearLayout) rootView.findViewById(R.id.fankui);
          mkefu = (TextView) rootView.findViewById(R.id.call);
         mgenxin = (LinearLayout) rootView.findViewById(R.id.udpate);

        mguanyu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActiviyt.class);
                startActivity(intent);
            }

        });


        mkehu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                intent.putExtra("url", "https://m.jicai.com/sever");
//                startActivity(intent);

                Intent intent = new Intent(getActivity(), CustomerActivity.class);
                startActivity(intent);


            }

        });

        mwenti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "http://m.jicai.com/fqa");
                startActivity(intent);

            }

        });


        mhuodong.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", "https://m.jicai.com/activity");
                startActivity(intent);
            }

        });



        mfenxiang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                intent.putExtra("url", "http://m.jicai.com/about");
//                startActivity(intent);

//                Intent intent = new Intent(getActivity(), SharesActivity.class);
//                startActivity(intent);

                umengShareUtils.share();//弹出分享面板

            }

        });

        mfankui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FankuiActivity.class);
                startActivity(intent);
            }

        });

       mgenxin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), UpdateActivity.class);
               startActivity(intent);
           }
       });

       mkefu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // 获取电话号码
               String mobile = mkefu.getText().toString();
               // 生成呼叫意图
               //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));  //直接拨打
               Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));   // 只进入拨号界面，不拨打
               // 开始呼叫
               startActivity(intent);


           }
       });
        return rootView;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umengShareUtils.authSSO(requestCode, resultCode, data);
    }




}
