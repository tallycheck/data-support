package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class CreateRequest extends InstanceRequest {
    public CreateRequest(String type) {
        super(type);
    }

    public CreateRequest(Class type) {
        super(type);
    }
}