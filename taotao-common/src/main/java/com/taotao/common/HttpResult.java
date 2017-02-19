package com.taotao.common;

/**
 * Created by shenchao on 2017/2/15.
 */
public class HttpResult {
    private Integer code;
    private String body;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HttpResult(Integer code, String body) {
        this.code = code;
        this.body = body;
    }

    public HttpResult() {
    }
}
