package com.jc.invite;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jc.base.Configure;
import com.jc.image.BitmapCache;
import com.jc.image.LoadImage;
import com.jc.R;
import com.jc.ui.CommonDisplayWindow;
import com.jc.ui.EnCodeDisplayWindow;
import com.jc.ui.FreshenList;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.utils.GsonRequest;
import com.jc.utils.UmengShareUtils;


/**
 * Created by zy on 15/8/10.
 */
public class InviteActivity extends HeadMenuActiviyt {

    protected FreshenList contentListview;

    private TextView inviteRule;
    private NetworkImageView merweima;

    private ImageLoader imageLoader;

    private LoadImage loadImage;

    private BitmapCache imageCache;

    private String encodeUrl;

    Bitmap bitmap;

    private LinearLayout mfriends;
    private String reqUrl = Configure.API_HOST+"/api/invite/list";
    private UmengShareUtils umengShareUtils;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_friends);
        contentListview = (FreshenList) findViewById(R.id.listView);
        mfriends = (LinearLayout) findViewById(R.id.friends_liner);

        getData();
        inviteRule = (TextView) findViewById(R.id.inviteRule);
        merweima = (NetworkImageView) findViewById(R.id.erweim);
        merweima.setDefaultImageResId(R.mipmap.erweima);
        merweima.setErrorImageResId(R.mipmap.erweima);
        imageCache = new BitmapCache();
        imageLoader = new ImageLoader(mQueue, imageCache);
        initView();
        setHeadTitleName("邀请好友");

        umengShareUtils = new UmengShareUtils(InviteActivity.this, "华盛达控股集团旗下互联网金融理财平台。即日起息，到期自动兑付，注册即送20元礼金和1000积分。");

        inviteRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDisplayWindow.show(InviteActivity.this, R.layout.invitation_rule_layout);
            }
        });

        merweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnCodeDisplayWindow.show(InviteActivity.this, R.layout.dimensioin_code, new LoadImage() {
                    @Override
                    public void load(String url) {
                    }

                    @Override
                    public void load(NetworkImageView imageView) {
                        imageView.setImageUrl(encodeUrl, imageLoader);
                    }
                });

            }
        });

        //邀请好友
        mfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                umengShareUtils.share();//弹出分享面板
            }
        });




        loadImage = new LoadImage() {
            @Override
            public void load(String url) {
                encodeUrl = url;
                merweima.setImageUrl(url,imageLoader);
            }

            @Override
            public void load(NetworkImageView imageView) {

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        InvitationAdapter adapter = new InvitationAdapter(this, new InvitationHolder(), contentListview, loadImage);
        request = new GsonRequest(reqUrl,adapter).withRequestParams("userId",app.getUserId().toString());
        addRequest(request);
    }

    public void getData() {

        // 调用MyListView中自定义接口
        contentListview.setonRefreshListener(new FreshenList.OnRefreshListener() {

            @Override
            public void onRefresh() {
                InvitationAdapter adapter = new InvitationAdapter(InviteActivity.this,new InvitationHolder(),contentListview,loadImage);
                request = new GsonRequest(reqUrl,adapter).withRequestParams("userId",app.getUserId().toString());
                addRequest(request);
                contentListview.onRefreshComplete();
            }
        });

    }

}
