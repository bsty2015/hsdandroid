package com.jc.bank;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.List;

/**
 * Created by lrh on 10/9/15.
 */
public class BankListWindow extends PopupWindow {

    private View bankList;

    protected ListView cardListview;

    private ImageView closeBankListButton;

    public BankListWindow(Activity context, List<Card> ownCards){
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        bankList = inflater.inflate(com.jc.R.layout.bank_card, null);
        closeBankListButton = (ImageView) bankList.findViewById(com.jc.R.id.closeBankListButton);
        //cardListview = (ListView) bindcardSelect.findViewById(R.id.cardList);

        //CardListAdapter adapter = new CardListAdapter(context,new CardListHolder(),cardListview);
        //adapter.attach(ownCards);
        //设置SelectPicPopupWindow的View

        this.setContentView(bankList);

        //设置SelectPicPopupWindow弹出窗体的宽

        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);

        //设置SelectPicPopupWindow弹出窗体的高

        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置SelectPicPopupWindow弹出窗体可点击

        this.setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果

        this.setAnimationStyle(com.jc.R.style.popupAnimation);

        //实例化一个ColorDrawable颜色为半透明

        ColorDrawable dw = new ColorDrawable(0xb0000000);

        //设置SelectPicPopupWindow弹出窗体的背景

        this.setBackgroundDrawable(dw);

        closeBankListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
