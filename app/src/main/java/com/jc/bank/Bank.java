package com.jc.bank;

/**
 * Created by lrh on 9/9/15.
 */
public class Bank {

    private Integer id;

    private String name;

    private String bankCode;

    private String cardNo;

    private Integer oneLimitAmt;

    private Integer dayLimitAmt;

    private Integer mothLimitAmt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getOneLimitAmt() {
        return oneLimitAmt;
    }

    public void setOneLimitAmt(Integer oneLimitAmt) {
        this.oneLimitAmt = oneLimitAmt;
    }

    public Integer getDayLimitAmt() {
        return dayLimitAmt;
    }

    public void setDayLimitAmt(Integer dayLimitAmt) {
        this.dayLimitAmt = dayLimitAmt;
    }

    public Integer getMothLimitAmt() {
        return mothLimitAmt;
    }

    public void setMothLimitAmt(Integer mothLimitAmt) {
        this.mothLimitAmt = mothLimitAmt;
    }
}
