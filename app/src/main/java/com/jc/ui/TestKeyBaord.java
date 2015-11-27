package com.jc.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;

import com.jc.component.CustomKeyBoard;


/**
 * Created by lrh on 18/8/15.
 */
public class TestKeyBaord extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.test_layout);
        ActionBar actionBar = getActionBar();
        actionBar.show();
        findViewById(com.jc.R.id.tts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomKeyBoard keyBoard = new CustomKeyBoard(TestKeyBaord.this,null);
                keyBoard.setRepeatSendCodeListener(new CustomKeyBoard.RepeatSendCodeListener() {
                    @Override
                    public void onRepeatSendCode() {

                    }
                });
                keyBoard.showAtLocation(TestKeyBaord.this.findViewById(com.jc.R.id.ttmain), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                keyBoard.onCountDown();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.jc.R.menu.menu_main,menu);
        return true;
    }
}
