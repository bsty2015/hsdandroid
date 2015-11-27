package com.jc.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.jc.base.Configure;
import com.jc.base.ResultData;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.ui.MainTabActivity;
import com.jc.user.User;
import com.jc.utils.GsonRequest;

import java.util.Map;

/**
 * Created by lrh on 19/8/15.
 */
public class InputPasswdActivity extends HeadMenuActiviyt {

    private EditText passwdInputView;

    private TextView addPasswdButton;

    private String passwd;

    private String telephone;

    private String code;

    private String registerUrl = Configure.API_HOST+"/api/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.input_password_layout);
        initView();
        passwdInputView = (EditText) findViewById(com.jc.R.id.passwdInput);
        addPasswdButton = (TextView) findViewById(com.jc.R.id.addPasswdButton);
        Intent intent = getIntent();
        telephone = intent.getStringExtra("telephone");
        code = intent.getStringExtra("code");
        passwdInputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tmp = passwdInputView.getText().toString();
                if (tmp != null && tmp.trim().length() > 5 && tmp.trim().length() < 17) {
                    passwd = tmp;
                    addPasswdButton.setBackgroundResource(com.jc.R.drawable.dodgerblue_background);
                } else {
                    addPasswdButton.setBackgroundResource(com.jc.R.drawable.cheek_background);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addPasswdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = passwdInputView.getText().toString();
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
                                User user = gson.fromJson(gson.toJson(userMap), User.class);
                                app.setUser(user);
                                Intent intent = new Intent(InputPasswdActivity.this, MainTabActivity.class);
                                intent.putExtra("mainTab", "个人中心");
                                startActivity(intent);
                            } else {
                                Toast toast = Toast.makeText(InputPasswdActivity.this, response.getErrMsg(), Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                request.withRequestParams("telephone", telephone).withRequestParams("validateCode", code).withRequestParams("passwd", passwd) .withRequestParams("platform","android");
                addRequest(request);
                }
            }

            );
        }

        @Override
    protected void onStart() {
        super.onStart();
        hideBackText();
        setHeadTitleName("注册");
    }
}
