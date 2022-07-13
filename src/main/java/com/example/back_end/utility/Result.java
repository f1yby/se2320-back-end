package com.example.back_end.utility;

public class Result<T> {
    private Integer code;
    private T detail;
    private String msg;

    public Result() {}
    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.detail = null;
    }

    public Result(Integer code, String msg, T detail) {
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
