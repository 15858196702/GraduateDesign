package xyz.dean.tutor_manager.model;

import xyz.dean.tutor_manager.common.ResponseCode;

public class ResponseData<T> {
    private Integer code;
    private String  msg;
    private T       data;

    public ResponseData() { }
    public ResponseData(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }
    public ResponseData setCode(Integer code) {
        this.code = code;
        return this;
    }
    public String getMsg() {
        return msg;
    }
    public ResponseData setMsg(String msg) {
        this.msg = msg;
        return this;
    }
    public T getData() {
        return data;
    }
    private ResponseData setData(T data) {
        this.data = data;
        return this;
    }

    public static <D> ResponseData successResponse(D data) {
        return new ResponseData<>(ResponseCode.SUCCESS, "", data);
    }
    public static ResponseData<Exception> unknownError(Exception e) {
        return new ResponseData<>(ResponseCode.UNKNOWN_ERROR, "未知错误", e);
    }
    public static ResponseData errorResponse(int errorCode) {
        ResponseData response;
        switch (errorCode) {
            case ResponseCode.DB_ERROR:
                response = new ResponseData<>(errorCode, "数据库错误", null);
                break;
            case ResponseCode.NAME_IS_TAKEN:
                response = new ResponseData<>(errorCode, "用户名已被占用", null);
                break;
            case ResponseCode.WRONG_PWD:
                response = new ResponseData<>(errorCode, "用户密码错误", null);
                break;
            case ResponseCode.NOT_REGISTER:
                response = new ResponseData<>(errorCode, "用户未注册", null);
                break;
            default:
                response = new ResponseData<>(errorCode, "未处理错误", null);
        }
        return response;
    }
}
