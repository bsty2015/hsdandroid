package com.jc.invite;

import java.util.Date;

/**
 * Created by lrh on 30/8/15.
 */
public class Invitation {

    private Integer id;

    private Date inviteDate;

    private String telephone;

    private Integer award;

    private Boolean isInvest;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInviteDate() {
        return inviteDate;
    }

    public void setInviteDate(Date inviteDate) {
        this.inviteDate = inviteDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }

    public Boolean getIsInvest() {
        return isInvest;
    }

    public void setIsInvest(Boolean isInvest) {
        this.isInvest = isInvest;
    }
}
