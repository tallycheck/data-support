package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/4/16.
 */
public class InfoRequest extends Request {
    public InfoRequest(String type) {
        super(type);
    }

    public InfoRequest(Class type) {
        super(type);
    }
}