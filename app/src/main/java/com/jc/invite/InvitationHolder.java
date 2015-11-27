package com.jc.invite;

import android.view.View;
import android.widget.TextView;

import com.jc.base.Holder;
import com.jc.utils.DateFormatUtil;

/**
 * Created by lrh on 23/7/15.
 */
public class InvitationHolder implements Holder<Invitation> {

    TextView inviteDateView;
    TextView inviteeTelephoneView;
    TextView isInvestView;
    TextView inviteAwardView;

    public InvitationHolder(){

    }

    private InvitationHolder(View view){
        inviteDateView = (TextView) view.findViewById(com.jc.R.id.inviteDate);
        inviteeTelephoneView = (TextView) view.findViewById(com.jc.R.id.inviteeTelephone);
        isInvestView = (TextView) view.findViewById(com.jc.R.id.isInvest);
        inviteAwardView = (TextView) view.findViewById(com.jc.R.id.inviteAward);
    }

    @Override
    public Holder build(View view) {
        return new InvitationHolder(view);
    }

    @Override
    public void setData(Invitation obj) {
        inviteDateView.setText(DateFormatUtil.date2String(obj.getInviteDate(), DateFormatUtil.DATE_FORMAT));
        inviteeTelephoneView.setText(obj.getTelephone());
        inviteAwardView.setText("+"+obj.getAward());
        if(obj.getIsInvest()){
            isInvestView.setText("是");
        }else{
            isInvestView.setText("否");
        }

    }


}
