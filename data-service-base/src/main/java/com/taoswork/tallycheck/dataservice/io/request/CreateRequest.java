package com.taoswork.tallycheck.dataservice.io.request;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class CreateRequest extends InstanceRequest {
    public CreateRequest() {
        super();
    }

    public CreateRequest(RequestEntity entity, Locale locale) {
        super(entity, locale);
    }

    public CreateRequest(InstanceRequest req) {
        super(req);
    }
}
