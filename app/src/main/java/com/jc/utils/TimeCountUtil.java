package com.jc.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import com.jc.R;

/**
 * Created by bsty on 11/5/15.
 */
public class TimeCountUtil extends CountDownTimer {
    private Activity mActivity;
    //    private TextView textView;
    private Button btn;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimeCountUtil(long millisInFuture, long countDownInterval, Button btn, Activity mActivity) {
        super(millisInFuture, countDownInterval);
        this.mActivity = mActivity;
        this.btn = btn;
    }

//    /**
//     * @param millisInFuture    The number of millis in the future from the call
//     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
//     *                          is called.
//     * @param countDownInterval The interval along the way to receive
//     *                          {@link #onTick(long)} callbacks.
//     */
//    public TimeCountUtil(long millisInFuture, long countDownInterval, TextView textView, Activity mActivity) {
//        super(millisInFuture, countDownInterval);
//        this.mActivity = mActivity;
//        this.textView = textView;
//    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onTick(long millisUntilFinished) {
        //设置为不可点击
        btn.setClickable(false);
//        设置倒计时时间
        btn.setText(millisUntilFinished / 1000 + "s");
        //获取按钮的文字
        Spannable span = new SpannableString(btn.getText().toString());
        //将倒计时时间显示为白色
        span.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        btn.setText(span);
        btn.setBackground(mActivity.getResources().getDrawable(R.drawable.yanzhengma_djs));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onFinish() {
        btn.setText("重新获取");
        btn.setBackground(mActivity.getResources().getDrawable(R.drawable.yanzhengma_sj));
        btn.setClickable(true);
    }
}
