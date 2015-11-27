package com.jc.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.jc.base.Configure;
import com.jc.base.ResultData;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.ui.WaitDialog;
import com.jc.utils.GsonRequest;
import com.jc.utils.RegexUtil;
import com.jc.utils.StringUtils;

import java.util.Map;

/**
 * Created by zy on 15/8/15.
 */
public class ForgetPasswordActivtity extends HeadMenuActiviyt {


    private LinearLayout telephoneRegionView;

    private EditText telephoneView;

    private LinearLayout codeRegionView;

    private TextView codeView;

    private LinearLayout nextButton;

    private TextView sendCode;

    private String telephone;

    private Boolean isCanSubmit = Boolean.FALSE;

    private String validateTelUrl = Configure.API_HOST+"/api/validateTelephone";

    private String validateCodeUrl = Configure.API_HOST+"/api/msg/code/verify";

    private String sendCodeUrl = Configure.API_HOST+"/api/msg/code";

    private Boolean isCanRepeatSend = true;

    private String SEND_CODE_TEXT = "%s后重新发送";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.forget_password);
        initView();
        telephoneRegionView = (LinearLayout) findViewById(com.jc.R.id.telephoneRegionView);
        telephoneView = (EditText) findViewById(com.jc.R.id.telephoneView);
        codeRegionView = (LinearLayout) findViewById(com.jc.R.id.codeRegionView);
        codeView = (TextView) findViewById(com.jc.R.id.codeView);
        sendCode = (TextView) findViewById(com.jc.R.id.sendCode);
        nextButton = (LinearLayout) findViewById(com.jc.R.id.nextButton);
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCanRepeatSend) {
                    validateTel();
                    onCountDown();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    validateCode();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setHeadTitleName("忘记密码");
    }

    private void validateTel(){
        telephone = telephoneView.getText().toString();
        if(StringUtils.isEmpty(telephone)){
            showErrMsg("请输入手机号");
            return;
        }
        if(!RegexUtil.matchPhone(telephone)){
            showErrMsg("手机格式错误");
            return;
        }

        GsonRequest request = new GsonRequest(validateTelUrl, new Response.Listener<ResultData>() {

            @Override
            public void onResponse(ResultData response) {
                Map<String, Object> rs = (Map<String, Object>) response.getData();
                Boolean isExist = (Boolean) rs.get("isExist");
                if (response.isSucc() && isExist != null && isExist) {
                    sendCode();
                } else {
                    showErrMsg("该用户未注册");
                }
            }
        });
        request.withRequestParams("telephone", telephone);
        addRequest(request);
    }


    private void validateCode(){
        final String code = codeView.getText().toString();
        if(!RegexUtil.match(code,"\\d{6}")){
            showErrMsg("验证码格式错误");
            return;
        }
        final WaitDialog waitDialog = WaitDialog.show(ForgetPasswordActivtity.this);
        GsonRequest request = new GsonRequest(validateCodeUrl, new Response.Listener<ResultData>() {

            @Override
            public void onResponse(ResultData response) {
                waitDialog.dismiss();
                if (response.isSucc()) {
                    Intent intent = new Intent(ForgetPasswordActivtity.this, ModifyNewPasswordActivtity.class);
                    intent.putExtra("telephone", telephone);
                    intent.putExtra("code", code);
                    startActivity(intent);
                } else {
                    showErrMsg(response.getErrMsg());
                }
            }
        });
        request.withRequestParams("telephone", telephone).withRequestParams("code", code);
        mQueue.add(request);
    }
    private  void sendCode(){
        GsonRequest request = new GsonRequest(sendCodeUrl, new Response.Listener<ResultData>() {

            @Override
            public void onResponse(ResultData response) {
                    if(!response.isSucc()){
                        showErrMsg(response.getErrMsg());
                    }
            }
        });
        request.withRequestParams("telephone", telephone);
        mQueue.add(request);
    }

    public void onCountDown(){
        isCanRepeatSend = Boolean.FALSE;
        new Thread(new Runnable() {
            int num = 60;
            String numText = "";
            @Override
            public void run() {

                while (true) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (num < 10) {
                                numText ="0" + num + "s";
                            } else {
                                numText =num + "s";
                            }
                            sendCode.setText(String.format(SEND_CODE_TEXT,numText));
                            if(num == 0 ){
                                sendCode.setText("重新发送");
                            }
                        }
                    });
                    if(num == 0){
                        isCanRepeatSend = Boolean.TRUE;
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                    num--;
                }
            }
        }).start();
    }
}
