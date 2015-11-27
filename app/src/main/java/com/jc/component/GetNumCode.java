package com.jc.component;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jc.R;

/**
 * Created by zy on 15/11/5.
 */
public class GetNumCode extends PopupWindow {
    private LinearLayout repeatSendLayout;
    private TextView countDownNum;
    private Handler handler = new Handler();
    private View registView;
    private Boolean isCanRepeatSend = false;

    public TextView getCountDownNum() {
        return countDownNum;
    }

    public GetNumCode(Activity context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        registView = inflater.inflate(R.layout.registered_account, null);

        repeatSendLayout = (LinearLayout) registView.findViewById(com.jc.R.id.repeatSendLayout);
        countDownNum = (TextView) registView.findViewById(com.jc.R.id.countDownNum);

    }

    public void onCountDown(){
        isCanRepeatSend = Boolean.FALSE;
        countDownNum.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            int num = 60;
            @Override
            public void run() {

                while (true) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (num < 10) {
                                countDownNum.setText("0" + num + "s");
                                Log.d("倒计时", String.valueOf(num));
                            } else {
                                countDownNum.setText(num + "s");
                                Log.d("倒计时", String.valueOf(num));
                            }
                            if (num == 0) {
                                countDownNum.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    if(num == 0){
                        isCanRepeatSend = Boolean.TRUE;
                        countDownNum.setEnabled(true);
                        return;
                    }else {
                       countDownNum.setEnabled(false);
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
