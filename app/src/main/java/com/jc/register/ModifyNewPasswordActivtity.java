package com.jc.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.jc.base.Configure;
import com.jc.base.ResultData;
import com.jc.login.LoginActivity;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.ui.WaitDialog;
import com.jc.utils.GsonRequest;
import com.jc.utils.StringUtils;

/**
 * Created by zy on 15/8/15.
 */
public class ModifyNewPasswordActivtity extends HeadMenuActiviyt {


    private LinearLayout modifyNewPasswd;

    private EditText newPasswd;

    private String telephone;

    private String code;

    private String reqUrl = Configure.API_HOST+"/api/user/forgetPasswd";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.new_password_layout);
        initView();
        Intent intent = getIntent();
        telephone = intent.getStringExtra("telephone");
        code = intent.getStringExtra("code");
        modifyNewPasswd = (LinearLayout) findViewById(com.jc.R.id.modifyNewPasswd);
        newPasswd = (EditText) findViewById(com.jc.R.id.newPasswd);

        modifyNewPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyPasswd();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setHeadTitleName("忘记密码");

    }



    private void modifyPasswd(){
        String passwd = newPasswd.getText().toString();
        if(StringUtils.isEmpty(passwd)|| passwd.length() <6 || passwd.length() >18){
            showErrMsg("由6～18位数字,字母组成");
            return;
        }
        final WaitDialog waitDialog = WaitDialog.show(ModifyNewPasswordActivtity.this);
        GsonRequest request = new GsonRequest(reqUrl, new Response.Listener<ResultData>() {

            @Override
            public void onResponse(ResultData response) {
                waitDialog.dismiss();
                if (response.isSucc()) {
                    showErrMsg("密码修改成功");
                    Intent intent = new Intent(ModifyNewPasswordActivtity.this, LoginActivity.class);
                    intent.putExtra("source","mdofyPwd");
                    startActivity(intent);
                } else {
                    showErrMsg(response.getErrMsg());
                }
            }
        });
        request.withRequestParams("telephone", telephone).withRequestParams("validateCode", code).withRequestParams("passwd",passwd);
        addRequest(request);
    }

}
