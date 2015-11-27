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
 * 新版注册账号的入口
 * 添加了图片验证码功能
 * Created by zy on 15/8/15.
 */
public class RegisterActivtity extends HeadMenuActiviyt {

    private LinearLayout resendCoed;

    private LinearLayout registerContract;

    private EditText telephoneView, imgCodeView;

    private TextView regFirstButton;

    //密码输入框
    private EditText passwdInput;
    //电话号码
    private String telephone;

    private String validateTelUrl = Configure.API_HOST + "/api/validateTelephone";

    private String validateCodeUrl = Configure.API_HOST + "/api/msg/code/verify";

    private String sendCodeUrl = Configure.API_HOST + "/api/msg/generateCode";

    private String getImgCodeUrl = Configure.API_HOST + "/api/captcha/getImage";
    //注册新用户的url
    private String registerUrl = Configure.API_HOST + "/api/register";
    private String code;

    private GetNumCode keyBoard;

    private Boolean isValidateTel = Boolean.FALSE;

    //图片验证码
    private NetworkImageView imgCodeItem;

    //图片验证码对应的key，由客户端自己生成（时间戳+6位随机数字）MD5加密之后的字符串，每一张图片验证码对应唯一的key
    private String imgRequestKey;

    //图片验证码
    private String imgCode;

    //手机短信验证码
    private String numCode;
    //手机短信验证码输入框
    private EditText numCodeText;
    //获取短信验证码的点击按钮,会提醒用户再次可以获取验证码的时间
    private Button resendNumCode;

    private LinearLayout registerAccountBtn;
    //密码明文显示开关
    private ImageView showPasswd;
    //是否明文显示密码的标记
    private boolean isShowPasswd = false;
    //手机号码是否被注册标记
    private boolean isRegistered = false;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.registered_account);
        initView();

        resendCoed = (LinearLayout) findViewById(com.jc.R.id.resendCoed);
        telephoneView = (EditText) findViewById(com.jc.R.id.regTelephone);
        // regFirstButton = (TextView) findViewById(com.jc.R.id.regFirst);
        registerContract = (LinearLayout) findViewById(com.jc.R.id.registerContract);
        imgCodeItem = (NetworkImageView) findViewById(R.id.code_img);
        passwdInput = (EditText) findViewById(R.id.passwdInput);
        imgCodeView = (EditText) findViewById(R.id.imgCodeView);
        numCodeText = (EditText) findViewById(R.id.num_code_text);
        resendNumCode = (Button) findViewById(R.id.resend_num_code);
        //是否将密码显示为明文
        showPasswd = (ImageView) findViewById(R.id.is_show_passwd);
        registerAccountBtn = (LinearLayout) findViewById(R.id.register_account_btn);

        /**
         * 明文显示密码的开关
         */
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

        //初始条件下，注册按钮背景颜色为灰色，表示不可点击
        registerAccountBtn.setBackground(getResources().getDrawable(R.drawable.confirm_background));
        showCodeImg();

        //重新获取短信验证码的监听器，填写正确格式手机号码并且未被注册，以及正确的图片验证码才能获取短信验证码
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
                                sendCode();
                            } else {
                                String msg = response.getErrMsg();
                                if (isExist) {
                                    msg = "手机号已被注册";
                                }
                                showErrMsg(msg);
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
                Intent intent = new Intent(RegisterActivtity.this, WebViewActivity.class);
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

        /**
         * 注册确定按钮的监听器
         */
        registerAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (telephone == null || telephone.isEmpty() || imgCode == null || imgCode.isEmpty()) {
                    return;
                }
                registerCount();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        isValidateTel = Boolean.FALSE;
        hideBackText();
        setHeadTitleName("注册集财账号");
        settitlebackground();
    }

    private void displayCodeWindow() {
        sendCode();
        TimeCountUtil timeCountUtil = new TimeCountUtil(60 * 1000, 1000, resendNumCode, RegisterActivtity.this);
        timeCountUtil.start();
        // sendCode();
//       keyBoard = new CustomKeyBoard(RegisterActivtity.this, new CustomKeyBoard.CompleteListener() {
//
//            @Override
//            public void onComplete(final CustomKeyBoard customKeyBoard) {
//                final String code =customKeyBoard.getCodes();
//                request = new GsonRequest(validateCodeUrl, new Response.Listener<ResultData>() {
//
//                    @Override
//                    public void onResponse(ResultData response) {
//                        if (response.isSucc()) {
//                            Intent intent = new Intent(RegisterActivtity.this, InputPasswdActivity.class);
//                            intent.putExtra("telephone", telephone);
//                            intent.putExtra("code", code);
//                            startActivity(intent);
//                        } else {
//                            customKeyBoard.setMsgTip(response.getErrMsg());
//                            customKeyBoard.clear();
//                        }
//                    }
//                });
//                request.withRequestParams("telephone", telephone).withRequestParams("code", code);
//                mQueue.add(request);
//            }
//        });
//        keyBoard.setRepeatSendCodeListener(new CustomKeyBoard.RepeatSendCodeListener() {
//            @Override
//            public void onRepeatSendCode() {
//                sendCode();
//            }
//        });


//        sendCode();
//        keyBoard.onCountDown();
//        keyBoard.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        keyBoard.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        keyBoard.showAtLocation(RegisterActivtity.this.findViewById(com.jc.R.id.countDownNum), Gravity.TOP | Gravity.CENTER, 0, 0);
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
                        TimeCountUtil timeCountUtil = new TimeCountUtil(60 * 1000, 1000, resendNumCode, RegisterActivtity.this);
                        timeCountUtil.start();

                    } else {
                        showErrMsg(response.getErrMsg());
                        return;
                    }
                }
            });
            request.withRequestParams("telephone", telephone).withRequestParams("key", imgRequestKey).withRequestParams("imgCode", imgCode);

            mQueue.add(request);
        } else {
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
    private void registerCount() {
        String tmp = passwdInput.getText().toString();
        if (tmp == null || tmp.trim().length() < 6 || tmp.trim().length() > 18) {
            showErrMsg("密码必须为6-18字符");
            return;
        }
        GsonRequest request = new GsonRequest(registerUrl, new Response.Listener<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                if (response.isSucc()) {
                    Map<String, Object> rs = (Map<String, Object>) response.getData();
                    Object userMap = rs.get("user");
                    User uer = gson.fromJson(gson.toJson(userMap), User.class);
                    app.setUser(uer);
                    Intent intent = new Intent(RegisterActivtity.this, MainTabActivity.class);
                    intent.putExtra("mainTab", "个人中心");
                    startActivity(intent);
                    showErrMsg("注册成功");
                } else {
                    Log.d("注册返回码：", response.getErrCode());
                    Toast.makeText(RegisterActivtity.this, response.getErrMsg(), Toast.LENGTH_SHORT).show();
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

    /**
     * 检测手机号是是否已经注册
     *
     * @param num
     */
    private boolean isNumRegistered(String num) {
        request = new GsonRequest(validateTelUrl, new Response.Listener<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                Map<String, Object> rs = (Map<String, Object>) response.getData();
                Boolean isExist = (Boolean) rs.get("isExist");
                if (response.isSucc() && isExist != null && isExist) {
                    isRegistered = true;
                } else {
                    isRegistered = false;
                }
            }
        });
        request.withRequestParams("telephone", num);
        addRequest(request);
        return isRegistered;
    }
}
 