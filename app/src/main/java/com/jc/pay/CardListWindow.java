package com.jc.pay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.jc.bank.AddCardActivity;
import com.jc.bank.Card;
import com.jc.R;

import java.util.List;

/**
 * Created by lrh on 10/9/15.
 */
public class CardListWindow extends PopupWindow {

    private View bindcardSelect;

    protected ListView cardListview;

    private ImageView closeCardSelect;

    private LinearLayout addNewCard;

    private Activity context;

    public CardListWindow(Activity context,List<Card> ownCards,final PayHolder payHolder){
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        bindcardSelect = inflater.inflate(R.layout.bindcard_select, null);
        cardListview = (ListView) bindcardSelect.findViewById(R.id.cardList);
        closeCardSelect = (ImageView) bindcardSelect.findViewById(R.id.closeCardSelect);
        addNewCard = (LinearLayout) bindcardSelect.findViewById(R.id.addNewCard);
        CardListAdapter adapter = new CardListAdapter(context,new CardListHolder(payHolder.getSelectedCardId()),cardListview);
        adapter.attach(ownCards);
        init();
        registerCancelListener(closeCardSelect);
        registerAddCardListener(addNewCard);
        cardListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) parent.getItemAtPosition(position);
                payHolder.changCard(card);
                dismiss();
            }
        });


    }

    private void init(){
        this.setContentView(bindcardSelect);

        //设置SelectPicPopupWindow弹出窗体的宽

        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);

        //设置SelectPicPopupWindow弹出窗体的高

        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置SelectPicPopupWindow弹出窗体可点击

        this.setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果

        this.setAnimationStyle(R.style.popupAnimation);

        //实例化一个ColorDrawable颜色为半透明

        ColorDrawable dw = new ColorDrawable(0xb0000000);

        //设置SelectPicPopupWindow弹出窗体的背景

        this.setBackgroundDrawable(dw);
    }

    private void registerCancelListener(ImageView closeCardSelect){
        closeCardSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void registerAddCardListener(LinearLayout addNewCard){
        addNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,AddCardActivity.class));
                dismiss();
            }
        });
    }
}
