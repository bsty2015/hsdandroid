package com.jc.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.jc.base.Configure;
import com.jc.base.ResultData;
import com.google.gson.Gson;
import com.jc.R;
import com.jc.register.ForgetPasswordActivityNew;
import com.jc.register.ForgetPasswordActivtity;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.register.RegisterActivtity;
import com.jc.ui.MainTabActivity;
import com.jc.user.User;
import com.jc.utils.GsonRequest;
import com.jc.utils.RegexUtil;

import java.util.Map;

/**
 * Created by lrh on 9/8/15.
 */
public class LoginActivity extends HeadMenuActiviyt {

    private String reqUrl = Configure.API_HOST+"/api/login";

    private View userNameRegion;

    private EditText userNameView;

    private View passwdRegion;

    private EditText passwdView;

    private View loginBtnRegion;

    private TextView loginBtn;

    private String userName ="";

    private String passwd ="";

    private Boolean isCanSubmit = Boolean.FALSE;

    private Gson gson = new Gson();



    private LinearLayout mzhuce;

    private TextView mwjmima;

    private String source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_on);
        initView();
        Intent intent =getIntent();
        source = intent.getStringExtra("source");
        userNameRegion = findViewById(R.id.userNameRegion);
        userNameView = (EditText) findViewById(R.id.userName);
        passwdRegion = findViewById(R.id.passwdRegion);
        passwdView = (EditText) findViewById(R.id.passwd);
        loginBtnRegion = findViewById(R.id.loginBtnRegion);
        mzhuce = (LinearLayout) findViewById(R.id.zhuce);
        mwjmima = (TextView) findViewById(R.id.wjimima);
        loginBtn = (TextView) findViewById(R.id.loginBtn);

        userNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tmp = userNameView.getText().toString();
                if (tmp != null) {
                    userName = tmp;
                }
                checkLoginInfo(userName, passwd);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwdView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tmp = passwdView.getText().toString();
                if (tmp != null) {
                    passwd = tmp;
                }
                checkLoginInfo(userName, passwd);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginBtnRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCanSubmit) {
                    request = new GsonRequest(reqUrl, new Response.Listener<ResultData>() {

                        @Override
                        public void onResponse(ResultData response) {
                            if (response.isSucc()) {
                                Map<String, Object> rs = (Map<String, Object>) response.getData();
                                Object userMap = rs.get("user");
                                User user = gson.fromJson(gson.toJson(userMap), User.class);
                                app.setUser(user);
                                skip();
                            } else {
                                Toast toast = Toast.makeText(LoginActivity.this, response.getErrMsg(), Toast.LENGTH_SHORT);
                                //toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    });
                    request.withRequestParams("userName", userName).withRequestParams("passwd", passwd);
                    addRequest(request);
                }
            }
        });





        mzhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivtity.class);
                startActivity(intent);
            }
        });


        mwjmima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(LoginActivity.this,ForgetPasswordActivityNew.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        hideBackButton();
        setBackText("取消");
        setHeadTitleName("");
    }

    private void checkLoginInfo(String userName,String passwd){
        if(RegexUtil.matchPhone(userName.trim())&& RegexUtil.match(passwd.trim(),RegexUtil.CHAR_REX)){
            loginBtnRegion.setBackgroundResource(R.drawable.greenblue_background);
            loginBtn.setTextColor(loginBtn.getResources().getColor(R.color.whites));
            isCanSubmit = Boolean.TRUE;
        }else{
            loginBtnRegion.setBackgroundResource(R.drawable.cheek_background);
            isCanSubmit = Boolean.FALSE;
        }
    }

    private void  skip(){
        if("mdofyPwd".equals(source)){
            Intent intent =new Intent(LoginActivity.this,MainTabActivity.class);
            intent.putExtra("mainTab", "个人中心");
            startActivity(intent);
        }else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;

    }
}
