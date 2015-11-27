package com.jc.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.R;

/**
 * Created by zy on 15/8/13.
 */
public class FankuiActivity extends HeadMenuActiviyt{
    private TextView mfanku,mcontent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        setHeadTitleName("意见反馈");
        initView();


            //获取按钮
            mfanku=(TextView)findViewById(R.id.fankuis);
            mcontent =(EditText)findViewById(R.id.add_content);

            //添加点击事件 ，保存文本信息，并生成提示，同时跳转到主界面
            mfanku.setOnClickListener(new Button.OnClickListener()
            {
                public void onClick(View v){
                    String Context =mcontent.getText().toString();;
                    //保存
                    try{
                        //调用网络接口，实现登陆指令
                      FankuiActivity.this.finish();
                    }

                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally{

                    }
                }
            });


        }





    }





