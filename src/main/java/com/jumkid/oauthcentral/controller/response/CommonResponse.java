package com.jumkid.oauthcentral.controller.response;

public class CommonResponse<T> {

    public enum ErrorCodes {
        ERROR_DB("error_db"), ERROR_VALIDATION("error_validation");

        private String code;

        private ErrorCodes(String code){ this.code = code; }

        public String code(){ return code; }
    }

    //indicate response successful or failed
    private boolean success = true;
    //error code for error reference
    private String errorCode = "";
    //some information for success or error
    private String msg;
    //the data json object
    private T data;

    public CommonResponse(T data){
        this.data = data;
    }

    public CommonResponse(String msg) { this.msg = msg; }

    public CommonResponse(String errorCode, String msg) {
        this.success = false;
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
