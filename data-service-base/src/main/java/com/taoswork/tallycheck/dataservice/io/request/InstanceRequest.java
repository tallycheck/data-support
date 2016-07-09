package com.taoswork.tallycheck.dataservice.io.request;

/**
 * Created by gaoyuan on 7/1/16.
 */
public abstract class InstanceRequest extends Request {
    public InstanceRequest(RequestEntity entity) {
        super(entity.getType());
        this.entity = entity;
    }

    public RequestEntity entity;
}
