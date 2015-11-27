package com.jc.capital;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lrh on 10/9/15.
 */
public class InvestProDetail {

    private Integer productId;

    private Integer investRecordId;

    private String  productName;

    private Date investDate;

    private Date expireDate;

    private BigDecimal corpus;

    private BigDecimal profit;

    private String bankName;

    private String bankCode;

    private String cardLast;

    private String orderId;

    private String status;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getInvestRecordId() {
        return investRecordId;
    }

    public void setInvestRecordId(Integer investRecordId) {
        this.investRecordId = investRecordId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getInvestDate() {
        return investDate;
    }

    public void setInvestDate(Date investDate) {
        this.investDate = investDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public BigDecimal getCorpus() {
        return corpus;
    }

    public void setCorpus(BigDecimal corpus) {
        this.corpus = corpus;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
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

    public String getCardLast() {
        return cardLast;
    }

    public void setCardLast(String cardLast) {
        this.cardLast = cardLast;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
