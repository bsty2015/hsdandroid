package com.jc.bank;

/**
 * Created by lrh on 10/9/15.
 */
public class Card {

    private Integer id;

    private String bankName;

    private String bankCode;

    private Integer oneLimitAmt;

    private Integer dayLimitAmt;

    private Integer mothLimitAmt;

    private String  cardLast;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public String getCardLast() {
        return cardLast;
    }

    public void setCardLast(String cardLast) {
        this.cardLast = cardLast;
    }
}
