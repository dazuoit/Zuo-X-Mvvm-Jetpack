package com.zuo.demo.lib_common.model;


public class EventMsg {
    private String mTag;//消息tag
    private Object mData;//消息内容

    public EventMsg(String tag, Object object) {
        mTag = tag;
        mData = object;
    }

    public String getTag() {
        return mTag;
    }

    public Object getData() {
        return mData;
    }
}
