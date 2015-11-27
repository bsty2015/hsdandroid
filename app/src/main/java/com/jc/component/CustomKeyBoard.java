package com.jc.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Handler;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Stack;

/**
 * Created by lrh on 18/8/15.
 */
public class CustomKeyBoard extends PopupWindow {

    private final static int CodeDelete   = -5;

    private final static int CodeSpace   = -4;

    private int index = 0;

    private EditText[] inputET = new EditText[6];

    private View keyBoardView;

    private ImageView keyboardCancelView;

    private TextView errMSGTip;

    private Stack<String> codeVal = new Stack<String>();

    private  CompleteListener completeListener;

    private LinearLayout repeatSendLayout;

    private CustomKeyBoard customKeyBoard;

    private TextView countDownNum;

    private Boolean isCanRepeatSend = false;

    private Handler handler = new Handler();

    public CustomKeyBoard(Activity context){
        customKeyBoard=this;
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        keyBoardView = inflater.inflate(com.jc.R.layout.keyboard_layout, null);

        repeatSendLayout = (LinearLayout) keyBoardView.findViewById(com.jc.R.id.repeatSendLayout);
        countDownNum = (TextView) keyBoardView.findViewById(com.jc.R.id.countDownNum);

    }

    public CustomKeyBoard(Activity context,CompleteListener completeListener){
        super(context);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.completeListener = completeListener;
        customKeyBoard = this;
        keyBoardView = inflater.inflate(com.jc.R.layout.keyboard_layout, null);
        inputET[0] = (EditText) keyBoardView.findViewById(com.jc.R.id.yz1);
        notPopKey(inputET[0]);
        inputET[1] = (EditText) keyBoardView.findViewById(com.jc.R.id.yz2);
        notPopKey(inputET[1]);
        inputET[2] = (EditText) keyBoardView.findViewById(com.jc.R.id.yz3);
        notPopKey(inputET[2]);
        inputET[3] = (EditText) keyBoardView.findViewById(com.jc.R.id.yz4);
        notPopKey(inputET[3]);
        inputET[4] = (EditText) keyBoardView.findViewById(com.jc.R.id.yz5);
        notPopKey(inputET[4]);
        inputET[5] = (EditText) keyBoardView.findViewById(com.jc.R.id.yz6);
        notPopKey(inputET[5]);

        keyboardCancelView = (ImageView) keyBoardView.findViewById(com.jc.R.id.keyboardCancel);
        errMSGTip = (TextView) keyBoardView.findViewById(com.jc.R.id.errMSGTip);
        repeatSendLayout = (LinearLayout) keyBoardView.findViewById(com.jc.R.id.repeatSendLayout);
        countDownNum = (TextView) keyBoardView.findViewById(com.jc.R.id.countDownNum);
        countDownNum.setVisibility(View.INVISIBLE);

        Keyboard mKeyboard= new Keyboard(context, com.jc.R.xml.keyboard);

        // Lookup the KeyboardView
        KeyboardView mKeyboardView= (KeyboardView)keyBoardView.findViewById(com.jc.R.id.keyboardview);
        // Attach the keyboard to the view
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);

        keyboardCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        //设置SelectPicPopupWindow的View

        this.setContentView(keyBoardView);

        //设置SelectPicPopupWindow弹出窗体的宽

        this.setWidth(LayoutParams.FILL_PARENT);

        //设置SelectPicPopupWindow弹出窗体的高

        this.setHeight(LayoutParams.WRAP_CONTENT);

        //设置SelectPicPopupWindow弹出窗体可点击

        this.setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果

        //this.setAnimationStyle(android.support.v7.appcompat.R.style.);

        //实例化一个ColorDrawable颜色为半透明

        ColorDrawable dw = new ColorDrawable(0xb0000000);

        //设置SelectPicPopupWindow弹出窗体的背景

        this.setBackgroundDrawable(dw);
        imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);

    }

    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override public void onKey(int primaryCode, int[] keyCodes) {

            if(primaryCode == CodeSpace){
                return;
            }

            // Handle key
            if( primaryCode==CodeDelete ) {

                if(index != 0 ){
                    index--;
                }
                EditText edittext = (EditText) inputET[index];
                Editable editable = edittext.getText();
                if( editable!=null ) editable.clear();
                if(!codeVal.isEmpty()){
                    codeVal.pop();
                }


            } else {// Insert character
                if(index == 6){
                    index = 5;
                }
                EditText edittext = (EditText) inputET[index];
                Editable editable = edittext.getText();
                if( editable!=null ) editable.clear();
                int start = edittext.getSelectionStart();
                String code = Character.toString((char) primaryCode);
                editable.insert(start, code);
                codeVal.push(code);
                if(index == 5 && completeListener != null){
                    completeListener.onComplete(customKeyBoard);
                }
                index++;
            }
        }

        @Override public void onPress(int arg0) {
        }

        @Override public void onRelease(int primaryCode) {
        }

        @Override public void onText(CharSequence text) {
        }

        @Override public void swipeDown() {
        }

        @Override public void swipeLeft() {
        }

        @Override public void swipeRight() {
        }

        @Override public void swipeUp() {
        }
    };

    public void setMsgTip(final String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                errMSGTip.setText(msg);
            }
        });
    }

    public String getCodes(){
        StringBuilder sb = new StringBuilder();
        while(!codeVal.isEmpty()){
            sb.append(codeVal.pop());
        }
        return sb.reverse().toString();
    }

    public static interface CompleteListener{

        void onComplete(final CustomKeyBoard customKeyBoard);

    }

    public static interface RepeatSendCodeListener{

        void onRepeatSendCode();

    }


    public void  setRepeatSendCodeListener(final RepeatSendCodeListener repeatSendCodeListener){
        repeatSendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCanRepeatSend){
                    repeatSendCodeListener.onRepeatSendCode();
                    onCountDown();
                }

            }
        });
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
                            } else {
                                countDownNum.setText(num + "s");
                            }
                            if (num == 0) {
                                countDownNum.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    if(num == 0){
                        isCanRepeatSend = Boolean.TRUE;
                        return;
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

    public void clear(){
        for (EditText et:inputET){
            et.setText("");
        }
        getCodes();
        index = 0;
    }

    private void notPopKey(EditText editText){
        editText.setKeyListener(null);
    }
}
