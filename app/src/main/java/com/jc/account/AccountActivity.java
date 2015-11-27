package com.jc.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.jc.bank.AddCardActivity;
import com.jc.base.Configure;
import com.jc.base.ResultData;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.ui.WaitDialog;
import com.jc.user.UserInfo;
import com.jc.utils.GsonRequest;

import java.util.Map;

/**
 * Created by zy on 15/8/10.
 */
public class AccountActivity extends HeadMenuActiviyt {

    private TextView telephone;

    private TextView realName;

    private TextView identity;

    private LinearLayout modifyPasswd;

    private LinearLayout authenticationButton;

    private String reqUrl = Configure.API_HOST+"/api/ user/index";

    private UserInfo userInfo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        userInfo = app.userInfo;
        if(userInfo == null || !userInfo.getIsVerify()){
            requestUserInfo();
        }else{
            displayContent();
        }
    }

    private void initAuthenticated(UserInfo userInfo){
        realName = (TextView) findViewById(com.jc.R.id.realName);
        realName.setText(userInfo.getRealName());
        identity = (TextView) findViewById(com.jc.R.id.identity);
        identity.setText(userInfo.getIdentity());
    }

    private void displayContent(){
        if(userInfo != null && userInfo.getIsVerify()){
            setContentView(com.jc.R.layout.authenticated);
            initAuthenticated(userInfo);
        }else{
            setContentView(com.jc.R.layout.not_authenticated);
            authenticationButton = (LinearLayout) findViewById(com.jc.R.id.authenticationButton);
        }
        initView();
        setHeadTitleName("用户信息");
        telephone = (TextView) findViewById(com.jc.R.id.telephone);
        telephone.setText(userInfo.getTelephone());

        modifyPasswd = (LinearLayout) findViewById(com.jc.R.id.modifyPasswd);
        modifyPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, ChangepasswordActivity.class));
            }
        });
        if(authenticationButton != null ){
            authenticationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AccountActivity.this,AddCardActivity.class));
                }
            });
        }
    }

    private void requestUserInfo(){
        final WaitDialog waitDialog = WaitDialog.show(AccountActivity.this);
        GsonRequest request = new GsonRequest(reqUrl, new Response.Listener<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                waitDialog.dismiss();
                try{
                    if(response.isSucc()){
                        Map<String,Object> rs = (Map<String, Object>) response.getData();
                        Object user = rs.get("userInfo");
                        userInfo = gson.fromJson(gson.toJson(user), UserInfo.class);
                        app.userInfo = userInfo;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayContent();
                            }
                        });
                    }else {
                        showErrMsg(response.getErrMsg());
                    }
                }catch (Exception e){

                }

            }
        });
        request.withRequestParams("userId",app.getUserId().toString());
        addRequest(request);
    }
}
