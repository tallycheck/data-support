package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class NewInstanceRequest extends Request {
    public NewInstanceRequest(String type) {
        super(type);
    }

    public NewInstanceRequest(Class type) {
        super(type);
    }
}
