package com.taoswork.tallycheck.dataservice.io.request;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class UpdateRequest extends InstanceRequest {
    public UpdateRequest() {
        super();
    }

    public UpdateRequest(RequestEntity entity, Locale locale) {
        super(entity, locale);
    }

    public UpdateRequest(InstanceRequest req) {
        super(req);
    }
}
