package com.jc.user;

import android.annotation.TargetApi;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.R;
import com.jc.base.Configure;
import com.jc.base.CustomApplication;
import com.jc.login.LoginActivity;
import com.jc.base.CommonFragment;
import com.jc.utils.BitmapUtils;
import com.jc.utils.GsonRequest;


/**
 * Created by zy on 15/8/3.
 */
public class UserFragment extends CommonFragment {

    private String reqUrl = Configure.API_HOST + "/api/user/index";

    private User user;

    private View loginDialogView;
    private TextView massets, mtodayncome, mincome;
    private LinearLayout mexitcount;
    private boolean isPrepared;
    private Bitmap btm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * 避免fragment重绘UI
         */
        if (rootView == null) {
            rootView = inflater.inflate(com.jc.R.layout.person_conter, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        isPrepared = true;
        isVisible = true;
        setHeadTitleName("用户中心");
        hideBackButton();
        loginDialogView = rootView.findViewById(com.jc.R.id.loginDialog);
        //动态设置背景图片，减少内存压力
        View loginBg = rootView.findViewById(R.id.loginBg);
        btm = BitmapUtils.getBitmapFromSource(getActivity(), R.mipmap.login_dialog1);
        loginBg.setBackgroundDrawable(new BitmapDrawable(btm));
        mexitcount = (LinearLayout) rootView.findViewById(com.jc.R.id.exit_count);
        app = (CustomApplication) getActivity().getApplication();

        if (app.getUser() == null) {
//            loginDialogView = rootView.findViewById(com.jc.R.id.loginDialog);
//            loginDialogView.setVisibility(View.VISIBLE);
            loginDialogView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

        mexitcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.removeUser();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(UserFragment.this).attach(UserFragment.this).commit();
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
//        lazyLoad();
//        loadData();
    }

    private void loadData() {
        user = app.getUser();
        if (user != null) {
            loginDialogView.setVisibility(View.INVISIBLE);
            UseFragmentAdapter adapter = new UseFragmentAdapter(getActivity(), new UserFragmentHoldr<UserInfo>(getActivity()), null);
            GsonRequest request = new GsonRequest(reqUrl, adapter);
            request.withRequestParams("userId", user.getId().toString());
            addRequest(request);
        } else {
            loginDialogView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        回收图片占用的内存
        if (btm != null) {
            btm.recycle();
            btm = null;
        }
        if (loginDialogView != null) {
            loginDialogView = null;
        }
        System.gc();
    }
}
