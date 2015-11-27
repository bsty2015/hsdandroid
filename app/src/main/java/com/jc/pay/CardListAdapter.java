package com.jc.pay;

import android.content.Context;
import android.widget.ListView;

import com.jc.bank.Card;
import com.jc.base.CommonAdapter;
import com.jc.base.Holder;
import com.jc.R;

/**
 * Created by lrh on 6/8/15.
 */
public class CardListAdapter extends CommonAdapter<Card> {


    public CardListAdapter(Context context, Holder<Card> holder, ListView listView){
        super(context,holder,listView,Boolean.FALSE);
    }

    @Override
    protected int getItemView() {
        return R.layout.bindcard_select_item;
    }


}
