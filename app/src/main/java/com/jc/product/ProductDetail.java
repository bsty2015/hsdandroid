package com.jc.product;

import java.math.BigDecimal;

/**
 * Created by lrh on 8/8/15.
 */
public class ProductDetail {

    private Integer id;

    private String name;

    private String status;

    private BigDecimal amt;

    private BigDecimal interestRate;

    private BigDecimal extraInterestRate;

    private Integer collectDuration;

    private String isNew;

    private BigDecimal remianAmt;

    private String activity;

    private BigDecimal limitAmt;

    private long date;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getExtraInterestRate() {
        return extraInterestRate;
    }

    public void setExtraInterestRate(BigDecimal extraInterestRate) {
        this.extraInterestRate = extraInterestRate;
    }

    public Integer getCollectDuration() {
        return collectDuration;
    }

    public void setCollectDuration(Integer collectDuration) {
        this.collectDuration = collectDuration;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public BigDecimal getLimitAmt() {
        return limitAmt;
    }

    public void setLimitAmt(BigDecimal limitAmt) {
        this.limitAmt = limitAmt;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public BigDecimal getRemianAmt() {
        return remianAmt;
    }

    public void setRemianAmt(BigDecimal remianAmt) {
        this.remianAmt = remianAmt;
    }
}
