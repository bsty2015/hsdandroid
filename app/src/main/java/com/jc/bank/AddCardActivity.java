package com.jc.bank;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Response;
import com.jc.base.Configure;
import com.jc.base.ResultData;
import com.jc.component.CustomKeyBoard;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.ui.WaitDialog;
import com.jc.user.UserInfo;
import com.jc.utils.GsonRequest;
import com.jc.utils.RegexUtil;

import java.util.Map;

import com.jc.utils.StringUtils;

/**
 * Created by zy on 15/8/10.
 */
public class  AddCardActivity extends HeadMenuActiviyt {

    private LinearLayout supportBankListView;

    private EditText userNameView;

    private EditText userIdentityView;

    private EditText newCardNoView;

    private EditText bindTelephoneView;

    private LinearLayout confirmButton;

    private String realName;

    private String identity;

    private String cardNo;

    private String telephone;

    private UserInfo userInfo;

    private Boolean isVerify = Boolean.FALSE;

    private String requestid;

    private CustomKeyBoard keyBoard;

    private String reqUrl = Configure.API_HOST+"/api/user/userInfo";

    private String reqBindUrl = Configure.API_HOST+"/api/pay/bindcard";

    private String validateCodeUrl = Configure.API_HOST+"/api/pay/bindcard/confirm";

    private String sendCodeUrl = Configure.API_HOST+"/api/pay/sendmsg";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.add_card);
        initView();
        setHeadTitleName("添加新卡");
        userNameView = (EditText) findViewById(com.jc.R.id.userName);
        userIdentityView = (EditText) findViewById(com.jc.R.id.userIdentity);
        newCardNoView = (EditText) findViewById(com.jc.R.id.newCardNo);
        bindTelephoneView = (EditText) findViewById(com.jc.R.id.bindTelephone);
        confirmButton = (LinearLayout) findViewById(com.jc.R.id.confirmButton);
        supportBankListView = (LinearLayout) findViewById(com.jc.R.id.supportBankListView);
        userInfo = app.userInfo;
        showUserInfo(userInfo);
        if(userInfo != null && !userInfo.getIsVerify()){
            request = new GsonRequest(reqUrl,new UserInfoListener()).withRequestParams("userId",app.getUserId().toString());
            mQueue.add(request);
        }
        init();
        initConfirmButton();
        supportBankListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankListWindow bankListWindow = new BankListWindow(AddCardActivity.this,null);
                bankListWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                bankListWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                bankListWindow.showAtLocation(findViewById(com.jc.R.id.addNewBank), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(userInfo != null && !userInfo.getIsVerify()) {
            GsonRequest request = new GsonRequest(reqUrl, new UserInfoListener()).withRequestParams("userId", app.getUserId().toString());
            addRequest(request);
        }
    }

    public class UserInfoListener implements Response.Listener<ResultData>{

        @Override
        public void onResponse(ResultData response) {
            Map<String,Object> rs = (Map<String, Object>) response.getData();
            Object data = rs.get("userInfo");
            if(data != null ){
                UserInfo userInfo = gson.fromJson(gson.toJson(data),UserInfo.class);
                app.userInfo = userInfo;
                showUserInfo(userInfo);
            }
        }
    }

    private void showUserInfo(UserInfo userInfo){
        if(userInfo != null && userInfo.getIsVerify()){
            isVerify = Boolean.TRUE;
            userNameView.setText(userInfo.getRealName());
            userIdentityView.setText(userInfo.getIdentity());
            userNameView.setKeyListener(null);
            userIdentityView.setKeyListener(null);
        }
    }

    private void init(){
        userNameView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    realName = userNameView.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        userIdentityView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    identity = userIdentityView.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        newCardNoView.addTextChangedListener(new TextWatcher() {

            int beforeTextLength=0;
            int onTextLength=0;
            boolean isChanged = false;

            int location=0;//记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String tmp = newCardNoView.getText().toString();
                if( tmp != null){
                    cardNo = tmp.replaceAll("\\s*", "");
                }
                beforeTextLength = s.length();
                if(buffer.length()>0){
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if(s.charAt(i) == ' '){
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if(onTextLength == beforeTextLength || onTextLength <= 3 || isChanged){
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isChanged){
                    location = newCardNoView.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if(buffer.charAt(index) == ' '){
                            buffer.deleteCharAt(index);
                        }else{
                            index++;
                        }
                    }
                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if((index == 4 || index == 9 || index == 14 || index == 19)){
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if(konggeNumberC>konggeNumberB){
                        location+=(konggeNumberC-konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if(location>str.length()){
                        location = str.length();
                    }else if(location < 0){
                        location = 0;
                    }
                    newCardNoView.setText(str);
                    Editable etable = newCardNoView.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }

            }
        });
        bindTelephoneView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                telephone = bindTelephoneView.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initConfirmButton(){
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVerify) {
                    if (StringUtils.isEmpty(realName)) {
                        showMsg("姓名不能为空");
                        return;
                    }
                    if (StringUtils.isEmpty(identity)) {
                        showMsg("身份证号不能为空");
                        return;
                    }
                }else{
                    realName = userNameView.getText().toString();
                    identity = userIdentityView.getText().toString();
                }

                if (StringUtils.isEmpty(cardNo)) {
                    showMsg("卡号不能为空");
                    return;
                }

                if (!RegexUtil.matchPhone(telephone)) {
                    showMsg("电话号码格式错误");
                    return;
                }
                final WaitDialog waitDialog = WaitDialog.show(AddCardActivity.this);
                GsonRequest request = new GsonRequest(reqBindUrl, new Response.Listener<ResultData>() {
                    @Override
                    public void onResponse(ResultData response) {
                        waitDialog.dismiss();
                        if (response.isSucc()) {
                            requestid = (String) ((Map<String, Object>) response.getData()).get("requestid");
                            validateCode();
                        } else {
                            requestid = null;
                            showMsg(response.getErrMsg());
                        }
                    }
                }).withRequestParams("userId", app.getUserId().toString());
                request.withRequestParams("cardno", cardNo);
                request.withRequestParams("idcardno", identity);
                request.withRequestParams("phone", telephone);
                request.withRequestParams("username", realName);
                addRequest(request);


            }
        });
    }

    private void validateCode(){
        keyBoard = new CustomKeyBoard(AddCardActivity.this, new CustomKeyBoard.CompleteListener() {

            @Override
            public void onComplete(final CustomKeyBoard customKeyBoard) {
                final String code = customKeyBoard.getCodes();
                request = new GsonRequest(validateCodeUrl, new Response.Listener<ResultData>() {

                    @Override
                    public void onResponse(ResultData response) {
                        if (response.isSucc()) {
                            finish();
                        } else {
                            customKeyBoard.setMsgTip(response.getErrMsg());
                            customKeyBoard.clear();
                        }
                    }
                });
                request.withRequestParams("requestid", requestid).withRequestParams("validatecode", code);
                request.withRequestParams("userId", app.getUserId().toString());
                mQueue.add(request);
            }
        });
        keyBoard.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        keyBoard.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        keyBoard.showAtLocation(AddCardActivity.this.findViewById(com.jc.R.id.addNewBank), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        keyBoard.onCountDown();
        keyBoard.setRepeatSendCodeListener(new CustomKeyBoard.RepeatSendCodeListener() {
            @Override
            public void onRepeatSendCode() {
                sendCode();
            }
        });
    }

    private void showMsg(String msg){
        Toast toast = Toast.makeText(AddCardActivity.this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private  void sendCode(){
        if(requestid == null ){
            return;
        }
        request = new GsonRequest(sendCodeUrl, new Response.Listener<ResultData>() {

            @Override
            public void onResponse(ResultData response) {

            }
        });
        request.withRequestParams("requestid", requestid);
        mQueue.add(request);
    }
}
