package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class UpdateRequest extends InstanceRequest {
    public UpdateRequest(String type) {
        super(type);
    }

    public UpdateRequest(Class type) {
        super(type);
    }
}
