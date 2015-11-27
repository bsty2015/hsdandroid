package com.jc.message;

import java.util.Date;

/**
 * Created by lrh on 30/8/15.
 */
public class Message {

    private Integer msgId;

    private String title;

    private String info;

    private String msgType;

    private String isRead;

    private String msgVisibleType;

    private Date sendTime;
    public int what;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getMsgVisibleType() {
        return msgVisibleType;
    }

    public void setMsgVisibleType(String msgVisibleType) {
        this.msgVisibleType = msgVisibleType;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
