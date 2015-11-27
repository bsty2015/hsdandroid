package com.jc.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.jc.base.Configure;
import com.jc.base.ResultData;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.ui.WaitDialog;
import com.jc.utils.GsonRequest;
import com.jc.utils.StringUtils;

/**
 * Created by zy on 15/8/12.
 */
public class ChangepasswordActivity extends HeadMenuActiviyt {

    private EditText oldPasswdView;

    private EditText newPasswdView;

    private LinearLayout confirmButton;

    private String reqUrl = Configure.API_HOST+"/api/user/changePasswd";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.change_password);
        initView();
        setHeadTitleName("修改密码");
        oldPasswdView = (EditText) findViewById(com.jc.R.id.oldPasswd);
        newPasswdView = (EditText) findViewById(com.jc.R.id.newPasswd);
        confirmButton = (LinearLayout) findViewById(com.jc.R.id.confirmButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPasswd = oldPasswdView.getText().toString();
                String newPasswd = newPasswdView.getText().toString();
                if (StringUtils.isEmpty(oldPasswd)) {
                    showMsg("原密码不能为空");
                    return;
                }

                if (StringUtils.isEmpty(oldPasswd) || newPasswd.trim().length() < 6 || newPasswd.trim().length() > 16){
                    showMsg("密码必须为6－16位");
                    return;
                }

                modifyPasswd(oldPasswd,newPasswd);
            }
        });


    }

    private void modifyPasswd(String oldPasswd,String newPasswd){
        final WaitDialog waitDialog = WaitDialog.show(ChangepasswordActivity.this);
        GsonRequest request = new GsonRequest(reqUrl, new Response.Listener<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                   waitDialog.dismiss();
                   if(response.isSucc()){
                       showMsg("密码修改成功");
                       finish();
                   }else {
                       showMsg(response.getErrMsg());
                   }
            }
        });
        request.withRequestParams("oldPasswd",oldPasswd);
        request.withRequestParams("newPasswd",newPasswd);
        request.withRequestParams("userId", app.getUserId().toString());
        addRequest(request);
    }

    private void showMsg(String msg){
        Toast toast = Toast.makeText(ChangepasswordActivity.this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
