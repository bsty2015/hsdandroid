package com.jc.product;

import java.math.BigDecimal;

/**
 * Created by lrh on 23/7/15.
 */
public class Product {

    private  Integer id;

    //产品剩余交易金额
    private BigDecimal remainAmt;

    private Integer duration;//产品融资期限

    private BigDecimal rate;

    private String status;

    private String name;

    private BigDecimal extraRate;//附加利率


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRemainAmt() {
        return remainAmt;
    }

    public void setRemainAmt(BigDecimal remainAmt) {
        this.remainAmt = remainAmt;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getExtraRate() {
        return extraRate;
    }

    public void setExtraRate(BigDecimal extraRate) {
        this.extraRate = extraRate;
    }
}
