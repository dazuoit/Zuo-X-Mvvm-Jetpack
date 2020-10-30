package com.zuo.demo.lib_common.net;

/**
 * FileName: ResponseThrowable
 * Author: zuo
 * Date: 2018/6/6
 * Description:基类数据
 * Version: 1.0
 */
public class ResponseThrowable extends Exception {
    public int code;
    public String message;

    public ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
