package com.jc.capital;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lrh on 29/8/15.
 */
public class Capital {

    private Integer id;

    private String orderId;

    private String productName;

    private BigDecimal corpus;

    private BigDecimal profit;

    private Date expireDate;

    private Date investDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getInvestDate() {
        return investDate;
    }

    public void setInvestDate(Date investDate) {
        this.investDate = investDate;
    }
}
