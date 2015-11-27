package com.jc.ui;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by lrh on 18/8/15.
 */
public class KeyBoardActivity extends Activity {

    public final static int CodeDelete   = -5;

    public final static int CodeSpace   = -4;

    public int index = 0;

    private EditText[] inputET = new EditText[6];

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.keyboard_layout);

        inputET[0] = (EditText) findViewById(com.jc.R.id.yz1);
        inputET[0].setInputType(InputType.TYPE_NULL);
        inputET[1] = (EditText) findViewById(com.jc.R.id.yz2);
        inputET[1].setInputType(InputType.TYPE_NULL);
        inputET[2] = (EditText) findViewById(com.jc.R.id.yz3);
        inputET[2].setInputType(InputType.TYPE_NULL);
        inputET[3] = (EditText) findViewById(com.jc.R.id.yz4);
        inputET[3].setInputType(InputType.TYPE_NULL);
        inputET[4] = (EditText) findViewById(com.jc.R.id.yz5);
        inputET[4].setInputType(InputType.TYPE_NULL);
        inputET[5] = (EditText) findViewById(com.jc.R.id.yz6);
        inputET[5].setInputType(InputType.TYPE_NULL);

        Keyboard mKeyboard= new Keyboard(this, com.jc.R.xml.keyboard);

        // Lookup the KeyboardView
        KeyboardView mKeyboardView= (KeyboardView)findViewById(com.jc.R.id.keyboardview);
        // Attach the keyboard to the view
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
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


            } else {// Insert character
                if(index == 6){
                    index = 5;
                }
                EditText edittext = (EditText) inputET[index];
                Editable editable = edittext.getText();
                if( editable!=null ) editable.clear();
                int start = edittext.getSelectionStart();
                editable.insert(start, Character.toString((char) primaryCode));
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
}
