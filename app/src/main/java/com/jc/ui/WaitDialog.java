package com.jc.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class WaitDialog extends Dialog {
	public WaitDialog(Context context) {
		super(context);
	}

	public WaitDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * 当窗口焦点改变时调用
	 */
	public void onWindowFocusChanged(boolean hasFocus) {
		ImageView imageView = (ImageView) findViewById(com.jc.R.id.spinnerImageView);
		// 获取ImageView上的动画背景
		AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
		// 开始动画
		spinner.start();
	}

	/**
	 * 给Dialog设置提示信息
	 * 
	 * @param message
	 */
	public void setMessage(CharSequence message) {
		if (message != null && message.length() > 0) {
			findViewById(com.jc.R.id.message).setVisibility(View.VISIBLE);
			TextView txt = (TextView) findViewById(com.jc.R.id.message);
			txt.setText(message);
			txt.invalidate();
		}
	}

	/**
	 * 弹出自定义ProgressDialog
	 * 
	 * @param context
	 *            上下文
	 * @param message
	 *            提示
	 * @param cancelable
	 *            是否按返回键取消
	 * @param cancelListener
	 *            按下返回键监听
	 * @return
	 */
	public static WaitDialog show(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
		WaitDialog dialog = new WaitDialog(context, com.jc.R.style.wait_dialog);
		dialog.setTitle("");
		dialog.setContentView(com.jc.R.layout.wait_dialog_layout);
		if (message == null || message.length() == 0) {
			dialog.findViewById(com.jc.R.id.message).setVisibility(View.GONE);
		} else {
			TextView txt = (TextView) dialog.findViewById(com.jc.R.id.message);
			txt.setVisibility(View.VISIBLE);
			txt.setText(message);
		}
		// 按返回键是否取消
		dialog.setCancelable(cancelable);
		// 监听返回键处理
		dialog.setOnCancelListener(cancelListener);
		// 设置居中
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		// 设置背景层透明度
		lp.dimAmount = 0.5f;
		dialog.getWindow().setAttributes(lp);
		// dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		dialog.show();
		return dialog;
	}

	public static WaitDialog show(Context context,String message) {

		return WaitDialog.show(context, message, false, null);
	}

	public static WaitDialog show(Context context) {

		return WaitDialog.show(context, "请求中...", false, null);
	}
}
