package com.master.app;

/**
 * Create By Master
 * On 2018/11/28 17:47
 */
public class Beans {

    /**
     * errorCode : 1
     * errorMessage : 发送失败: 短信验证码发送过频繁
     * data : null
     * pagerInfo : null
     */

    private String errorCode;
    private String errorMessage;
    private Object data;
    private Object pagerInfo;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getPagerInfo() {
        return pagerInfo;
    }

    public void setPagerInfo(Object pagerInfo) {
        this.pagerInfo = pagerInfo;
    }
}
