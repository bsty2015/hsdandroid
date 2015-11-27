package com.jc.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.capital.CapitalActivity;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.ui.MainTabActivity;

/**
 * Created by lrh on 12/9/15.
 */
public class PayCompleteActivity extends HeadMenuActiviyt {

    private LinearLayout backButton;

    private LinearLayout displayCapital;

    private TextView failureText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Boolean isSucc = intent.getBooleanExtra("isSucc", Boolean.FALSE);
        String msg = intent.getStringExtra("msg");
        if(isSucc){
            setContentView(com.jc.R.layout.pay_success);
            displayCapital = (LinearLayout) findViewById(com.jc.R.id.displayCapital);
        }else {
            setContentView(com.jc.R.layout.pay_failure);
            failureText = (TextView) findViewById(com.jc.R.id.failureText);
            failureText.setText(msg);
        }
        backButton = (LinearLayout) findViewById(com.jc.R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayCompleteActivity.this,MainTabActivity.class);
                intent.putExtra("mainTab","理财产品");
                startActivity(intent);
            }
        });

        if(displayCapital != null ){
            displayCapital.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PayCompleteActivity.this,CapitalActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this,MainTabActivity.class);
            intent.putExtra("mainTab","理财产品");
            startActivity(intent);
        }
        return true;
    }
}
