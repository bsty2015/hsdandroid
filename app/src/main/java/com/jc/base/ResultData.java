package com.jc.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lrh on 23/7/15.
 */
public class ResultData <T>{

    @SerializedName("err_code")
    private String errCode;

    @SerializedName("err_msg")
    private String errMsg;

    private  Page page;

    private T data;

    public Boolean isSucc(){
        return "0".equals(this.errCode);
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
