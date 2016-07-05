package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/1/16.
 */
public abstract class InstanceRequest extends Request {
    public InstanceRequest(String type) {
        super(type);
    }

    public InstanceRequest(Class type) {
        super(type);
    }
}
