package com.jc.details;

import com.jc.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import com.jc.utils.NumberUtils;

/**
 * Created by lrh on 14/9/15.
 */
public class DealDetail {

    private Integer id;

    private Integer userId;

    private Integer productId;

    private BigDecimal amt;

    private String info;

    private String productName;

    private String source;

    private Date repayDate;

    private Date date;

    private String status;

    private String type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc(){
        if(!StringUtils.isEmpty(info)){
            return info;
        }

        if(!StringUtils.isEmpty(productName)){
            return productName;
        }
        return source;
    }

    public String getPayInfo(){
        if(Double.valueOf(status).intValue() == 1){
            return "+"+ NumberUtils.formatNumber(amt);
        }
        return "-"+ NumberUtils.formatNumber(amt);
    }
}
