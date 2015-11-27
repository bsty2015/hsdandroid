package com.jc.user;

import java.math.BigDecimal;

/**
 * Created by zy on 15/9/10.
 */
public class UserInfo {
    private int id;
    private Boolean isExistMsg;
    private int creditsNum;
    private BigDecimal cashAmt;
    private BigDecimal todayTotalProfit;
    private String identity;
    private BigDecimal totalAssetAmt;
    private BigDecimal totalprofitAmt;
    private String userKey;
    private String realName;
    private int unpayNum;
    private Boolean isVerify;
    private String telephone;
    private int cardNum;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getIsExistMsg() {
        return isExistMsg;
    }

    public void setIsExistMsg(Boolean isExistMsg) {
        this.isExistMsg = isExistMsg;
    }

    public int getCreditsNum() {
        return creditsNum;
    }

    public void setCreditsNum(int creditsNum) {
        this.creditsNum = creditsNum;
    }

    public BigDecimal getCashAmt() {
        return cashAmt;
    }

    public void setCashAmt(BigDecimal cashAmt) {
        this.cashAmt = cashAmt;
    }

    public BigDecimal getTodayTotalProfit() {
        return todayTotalProfit;
    }

    public void setTodayTotalProfit(BigDecimal todayTotalProfit) {
        this.todayTotalProfit = todayTotalProfit;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public BigDecimal getTotalAssetAmt() {
        return totalAssetAmt;
    }

    public void setTotalAssetAmt(BigDecimal totalAssetAmt) {
        this.totalAssetAmt = totalAssetAmt;
    }

    public BigDecimal getTotalprofitAmt() {
        return totalprofitAmt;
    }

    public void setTotalprofitAmt(BigDecimal totalprofitAmt) {
        this.totalprofitAmt = totalprofitAmt;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int  getUnpayNum() {
        return unpayNum;
    }

    public void setUnpayNum(int unpayNum) {
        this.unpayNum = unpayNum;
    }

    public Boolean getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(Boolean isVerify) {
        this.isVerify = isVerify;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }


}
