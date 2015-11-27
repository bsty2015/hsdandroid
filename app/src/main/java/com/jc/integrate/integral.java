package com.jc.integrate;

import java.math.BigDecimal;
import java.util.Date;
/**
 * Created by zy on 15/8/30.
 */
public class integral {

    private BigDecimal totalCredit;//积分总数
    private Integer id;
    private Date  date;  //获取积分时间，值为毫秒数
    private String source; //积分的来源
    private BigDecimal award; //积分数量
    private int status;


    public BigDecimal getAward() {
        return award;
    }

    public void setAward(BigDecimal award) {
        this.award = award;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(BigDecimal totalCredit) {
        this.totalCredit = totalCredit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
