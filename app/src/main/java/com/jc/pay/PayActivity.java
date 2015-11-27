package com.jc.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.jc.bank.Card;
import com.jc.base.Configure;
import com.jc.base.ResultData;
import com.jc.component.CustomKeyBoard;
import com.jc.login.LoginActivity;
import com.jc.ui.CustomProgress;
import com.jc.ui.HeadMenuActiviyt;
import com.jc.ui.PayingDialog;
import com.jc.ui.WaitDialog;
import com.jc.user.User;
import com.jc.utils.GsonRequest;
import com.jc.utils.StringUtils;
import java.util.Map;


/**
 * Created by lrh on 10/9/15.
 */
public class PayActivity extends HeadMenuActiviyt {

    private String reqUrl = Configure.API_HOST+"/api/invest/pay";

    private String repeatReqUrl = Configure.API_HOST+"/api/invest/repeatpay";

    private String reqPayUrl = Configure.API_HOST+"/api/pay/invest";

    private String repeatReqPayUrl = Configure.API_HOST+"/api/pay/repeatpay";

    private String sendCodeUrl = Configure.API_HOST+"/api/pay/sendmsg";

    private String payConfirmUrl = Configure.API_HOST+"/api/pay/invest/confirm";

    private String payStatusCheckUrl = Configure.API_HOST+"/api/pay/invest/status";

    private ProductPayInfo productPayInfo;

    private Integer productId;

    private Integer cardId;

    private String orderId;

    Intent intent;

    private LinearLayout confirmPay;

    private EditText investAmtView;

    private String requestid;

    private CustomKeyBoard keyBoard;

    private PayingDialog payingDialog;

    private Boolean isPayed = Boolean.FALSE;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jc.R.layout.pay_products);
        initView();
        setHeadTitleName("购买");
        confirmPay = (LinearLayout)findViewById(com.jc.R.id.confirmPay);
        investAmtView = (EditText)findViewById(com.jc.R.id.investAmt);
        intent = getIntent();
        productId = intent.getIntExtra("productId", -1);
        orderId = intent.getStringExtra("orderId");
        user = app.getUser();
        if( user == null ){
            loginOut();
        }
        confirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPay();
            }
        });


    }

    private void loginOut(){
        Intent intent = new Intent(PayActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = app.getUser();
        if(user == null ){
            finish();
        }
        intent = getIntent();
        if(cardId == null ){
            cardId = intent.getIntExtra("cardId", -1);
        }
        if(StringUtils.isEmpty(orderId)){
            orderId = intent.getStringExtra("orderId");
        }
        requestProductData();
    }

    private void requestProductData(){
        String tmpUrl = reqUrl;
        if(!StringUtils.isEmpty(orderId)){
            tmpUrl = repeatReqUrl;
        }
        final CustomProgress pd = CustomProgress.show(this);
        GsonRequest request = new GsonRequest(tmpUrl, new Response.Listener<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                pd.dismiss();
                if("-2".equals(response.getErrCode())){
                    loginOut();
                    return;
                }
                if(response.isSucc()){
                    productPayInfo = gson.fromJson(gson.toJson(response.getData()),ProductPayInfo.class);
                    Card card = productPayInfo.getCard();
                    if( card != null){
                        cardId = card.getId();
                    }
                    final PayHolder payHolder = new PayHolder(PayActivity.this);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            payHolder.setData(productPayInfo);
                        }
                    });

                }
            }
        });
        request.withRequestParams("productId", formatNull(productId));
        request.withRequestParams("cardId",formatNull(cardId));
        request.withRequestParams("orderId",formatNull(orderId));
        request.withRequestParams("userId",app.getUserId().toString());
        addRequest(request);
    }

    /**
     * 发送投资请求
     */
    private void requestPay(){
        String investAmt = investAmtView.getText().toString();
        if(StringUtils.isEmpty(investAmt)){
            showMsg("投资金额不能为空");
            return;
        }
        String tmpUrl = reqPayUrl;
        if(!StringUtils.isEmpty(orderId)){
            tmpUrl = repeatReqPayUrl;
        }
        final WaitDialog waitDialog = WaitDialog.show(PayActivity.this,"请求中...");
        GsonRequest payRequest = new GsonRequest(tmpUrl, new Response.Listener<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                waitDialog.dismiss();
                if(response.isSucc()){
                    Map<String,String> data = (Map<String, String>) response.getData();
                    requestid = data.get("requestid");
                    sendPayConfirm();
                }else{
                    showMsg(response.getErrMsg());
                }
            }
        });
        payRequest.withRequestParams("productId", formatNull(productId));
        payRequest.withRequestParams("cardId", formatNull(cardId));
        payRequest.withRequestParams("orderId", formatNull(orderId));
        payRequest.withRequestParams("investAmt", investAmt);
        payRequest.withRequestParams("userId", app.getUserId().toString());
        addRequest(payRequest);
    }

    private String formatNull(Object val){
      if(val == null ){
          return "";
      }
        return val.toString();
    }

    public void setSelectedCardId(Integer cardId){
        this.cardId = cardId;
    }

    public Integer getSelectedCardId(){
            return this.cardId;
    }

    /**
     * 投资中发送给易宝短信确认
     */
    private void sendPayConfirm(){
        keyBoard = new CustomKeyBoard(PayActivity.this, new CustomKeyBoard.CompleteListener() {

            @Override
            public void onComplete(final CustomKeyBoard customKeyBoard) {
                final String code = customKeyBoard.getCodes();
                GsonRequest confirmRequest = new GsonRequest(payConfirmUrl, new Response.Listener<ResultData>() {

                    @Override
                    public void onResponse(ResultData response) {
                        if (response.isSucc()) {
                            keyBoard.dismiss();
                            checkPayStatus();
                            payingDialog = PayingDialog.show(PayActivity.this);
                        } else {
                            customKeyBoard.setMsgTip(response.getErrMsg());
                            customKeyBoard.clear();
                        }
                    }
                });
                confirmRequest.withRequestParams("requestid", requestid).withRequestParams("validatecode", code);
                confirmRequest.withRequestParams("userId", app.getUserId().toString());
                addRequest(confirmRequest);
            }
        });
        keyBoard.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        keyBoard.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        keyBoard.showAtLocation(PayActivity.this.findViewById(com.jc.R.id.topLayout), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        keyBoard.onCountDown();
        keyBoard.setRepeatSendCodeListener(new CustomKeyBoard.RepeatSendCodeListener() {
            @Override
            public void onRepeatSendCode() {
                sendCode();
            }
        });
    }

    private void checkPayStatus(){

        GsonRequest checkRequest = new GsonRequest(payStatusCheckUrl, new Response.Listener<ResultData>() {
            @Override
            public void onResponse(ResultData response) {
                String code = response.getErrCode();
                if("2".equals(code) || "3".equals(code)){
                    checkPayStatus();
                    return;
                }
                isPayed = Boolean.TRUE;
                if(payingDialog != null ){
                    payingDialog.dismiss();
                }
                Intent intent = new Intent(PayActivity.this,PayCompleteActivity.class);
                if(response.isSucc()){
                    intent.putExtra("isSucc",Boolean.TRUE);
                }else {
                    intent.putExtra("isSucc",Boolean.FALSE);
                    intent.putExtra("msg",response.getErrMsg());
                }
                startActivity(intent);
            }
        });
        checkRequest.withRequestParams("requestid", requestid);
        checkRequest.setTag("checkRequest");
        mQueue.add(checkRequest);

    }

    private  void sendCode(){
        if(requestid == null ){
            return;
        }
        request = new GsonRequest(sendCodeUrl, new Response.Listener<ResultData>() {

            @Override
            public void onResponse(ResultData response) {

            }
        });
        request.withRequestParams("requestid", requestid);
        addRequest(request);
    }

    private void showMsg(String msg){
        Toast toast = Toast.makeText(PayActivity.this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQueue.cancelAll("checkRequest");
    }
}
