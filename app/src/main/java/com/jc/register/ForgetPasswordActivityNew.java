package com.jc.register;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;
import com.jc.R;
import com.jc.base.Configure;
import com.jc.base.CustomApplication;
import com.jc.base.ResultData;
import com.jc.component.GetNumCode;
import com.jc.image.CommonLoadImage;
import com.jc.login.LoginActivity;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.ui.MainTabActivity;
import com.jc.ui.WebViewActivity;
import com.jc.user.User;
import com.jc.utils.CodeImageUtils;
import com.jc.utils.GsonRequest;
import com.jc.utils.RegexUtil;
import com.jc.utils.TimeCountUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 新版忘记密码入口
 * 和新版注册的方法类似，主要区别在于当用户手机号码已经注册过才能修改密码
 * Created by zy on 15/8/15.
 */
public class ForgetPasswordActivityNew extends HeadMenuActiviyt {

    private LinearLayout resendCoed;

    private LinearLayout registerContract;

    private EditText telephoneView, imgCodeView;

    private TextView regFirstButton;

    private EditText passwdInput;

    private String telephone;

    private String validateTelUrl = Configure.API_HOST + "/api/validateTelephone";

    private String validateCodeUrl = Configure.API_HOST + "/api/msg/code/verify";

    private String sendCodeUrl = Configure.API_HOST + "/api/msg/generateCode";

    private String getImgCodeUrl = Configure.API_HOST + "/api/captcha/getImage";
    //    private String registerUrl = Configure.API_HOST + "/api/register";
    private String registerUrl = Configure.API_HOST + "/api/user/forgetPasswd";
    private String code;

    private GetNumCode keyBoard;

    private Boolean isValidateTel = Boolean.FALSE;

    private NetworkImageView imgCodeItem;

    private String imgRequestKey;

    private String imgCode;

    private String numCode;
    private EditText numCodeText;
    //    private TextView resendNumCode;
    private Button resendNumCode;

    private LinearLayout registerAccountBtn;
    private ImageView showPasswd;
    private boolean isShowPasswd = false;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_new);
        initView();

        resendCoed = (LinearLayout) findViewById(R.id.resendCoed);
        telephoneView = (EditText) findViewById(R.id.regTelephone);
        // regFirstButton = (TextView) findViewById(com.jc.R.id.regFirst);
        registerContract = (LinearLayout) findViewById(R.id.registerContract);
        imgCodeItem = (NetworkImageView) findViewById(R.id.code_img);
        passwdInput = (EditText) findViewById(R.id.passwdInput);
        imgCodeView = (EditText) findViewById(R.id.imgCodeView);
//        keyBoard = new GetNumCode(RegisterActivtity.this);
//        countDownNum = keyBoard.getCountDownNum();
//        countDownNum.setText("点击获取");
        numCodeText = (EditText) findViewById(R.id.num_code_text);
        resendNumCode = (Button) findViewById(R.id.resend_num_code);
        //是否将密码显示为明文
        showPasswd = (ImageView) findViewById(R.id.is_show_passwd);
        registerAccountBtn = (LinearLayout) findViewById(R.id.register_account_btn);

        showPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isShowPasswd) {
                    //如果选中，隐藏密码
                    passwdInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPasswd.setImageResource(R.mipmap.login2);
                    isShowPasswd = false;
                } else {
                    //显示密码
                    passwdInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPasswd.setImageResource(R.mipmap.login1);
                    isShowPasswd = true;
                }

            }
        });


        registerAccountBtn.setBackground(getResources().getDrawable(R.drawable.confirm_background));
        showCodeImg();
//        countDownNum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayCodeWindow();
//            }
//        });
        resendNumCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String tmp = telephoneView.getText().toString();
                if (tmp != null && RegexUtil.matchPhone(tmp)) {
                    telephone = tmp;
                    request = new GsonRequest(validateTelUrl, new Response.Listener<ResultData>() {

                        @Override
                        public void onResponse(ResultData response) {
                            Map<String, Object> rs = (Map<String, Object>) response.getData();
                            Boolean isExist = (Boolean) rs.get("isExist");
                            if (response.isSucc() && isExist != null && !isExist) {
                                String msg = response.getErrMsg();
                                msg = "手机号码未注册";
                                showErrMsg(msg);
                            } else {

                                if (isExist) {
                                    sendCode();
                                }
                            }
                        }
                    });
                    request.withRequestParams("telephone", tmp);
                    addRequest(request);

                } else {
                    showErrMsg("请输入手机号");
                }

            }
        });

        telephoneView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String tmp = telephoneView.getText().toString();
                if (tmp != null && RegexUtil.matchPhone(tmp)) {
                    //registerAccountBtn.setBackgroundResource(R.drawable.zhuce_background);
                } else {
                    //registerAccountBtn.setBackgroundResource(com.jc.R.drawable.cheek_background);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        registerContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivityNew.this, WebViewActivity.class);
                intent.putExtra("title", "用户协议");
                intent.putExtra("url", Configure.H5_HOST + "/useragreement");
                startActivity(intent);
            }
        });

        /**
         * 给验证码图片添加点击事件，点击更换验证码图片
         * */
        imgCodeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCodeImg();
            }
        });

        //忘记密码
        registerAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (telephone == null || telephone.isEmpty() || imgCode == null || imgCode.isEmpty()) {
                    return;
                }
                forgetPassword();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        isValidateTel = Boolean.FALSE;
        hideBackText();
        setHeadTitleName("修改密码");
        settitlebackground();
    }

    /**
     * 点击获取手机验证码短信
     */
    private void sendCode() {
        imgCode = imgCodeView.getText().toString();
        if (imgCode != null && imgCode.length() == 4 && imgRequestKey != null) {

            request = new GsonRequest(sendCodeUrl, new Response.Listener<ResultData>() {

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(ResultData response) {
                    Log.d("请求验证码:", response.getErrCode());
                    Log.d("请求验证码信息:", response.getErrMsg());
                    if (response.getErrCode().equals("0")) {
                        registerAccountBtn.setBackground(getResources().getDrawable(R.drawable.zhuce_background));
                        TimeCountUtil timeCountUtil = new TimeCountUtil(60 * 1000, 1000, resendNumCode, ForgetPasswordActivityNew.this);
                        timeCountUtil.start();

                    } else {
                        showErrMsg(response.getErrCode());
                        return;
                    }
                }
            });
            request.withRequestParams("telephone", telephone).withRequestParams("key", imgRequestKey).withRequestParams("imgCode", imgCode);

            mQueue.add(request);
        } else if(imgCode.length()>0) {
            showErrMsg("图片验证码错误！");
            return;

        }else {
            showErrMsg("请输入图片验证码！");
            return;
        }

    }

    /**
     * 显示验证码
     */
    private void showCodeImg() {
        imgRequestKey = CodeImageUtils.generateImgKey();
        try {
            CommonLoadImage.getInstance(CustomApplication.mQueue).load(imgCodeItem, getImgCodeUrl + "?key=" + imgRequestKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 注册用户
     */
    private void forgetPassword() {
        String tmp = passwdInput.getText().toString();
        if (tmp == null || tmp.trim().length() < 6 || tmp.trim().length() > 18) {
            showErrMsg("密码必须为6-18字符");
            return;
        }
        GsonRequest request = new GsonRequest(registerUrl, new Response.Listener<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                if (response.isSucc()) {
                    showErrMsg("修改密码成功");
                    ForgetPasswordActivityNew.this.finish();
                } else {
                    Log.d("注册返回码：", response.getErrCode());
                    Toast.makeText(ForgetPasswordActivityNew.this, response.getErrMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        numCode = numCodeText.getText().toString();
        Map<String, String> registerParams = new HashMap<>();
        registerParams.put("telephone", telephone);
        registerParams.put("passwd", tmp);
        registerParams.put("validateCode", numCode);
        request.withRequestParams(registerParams);
        addRequest(request);
    }
}
 