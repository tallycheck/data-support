package com.taoswork.tallycheck.dataservice.io.request;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public abstract class InstanceRequest extends Request {
    public InstanceRequest() {
        super();
    }

    public InstanceRequest(RequestEntity entity, Locale locale) {
        super(entity.getType(), locale);
        this.entity = entity;
    }

    public InstanceRequest(InstanceRequest req) {
        super(req);
        this.entity = req.entity;
    }

    public RequestEntity entity;
}
